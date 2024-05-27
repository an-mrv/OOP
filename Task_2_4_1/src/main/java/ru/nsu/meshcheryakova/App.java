package ru.nsu.meshcheryakova;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.groovy.groovysh.Main;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.meshcheryakova.dsl.Course;
import ru.nsu.meshcheryakova.dsl.Group;
import ru.nsu.meshcheryakova.dsl.Student;
import ru.nsu.meshcheryakova.dsl.Task;
import ru.nsu.meshcheryakova.handling.HTMLResults;
import ru.nsu.meshcheryakova.handling.StudentInfo;
import ru.nsu.meshcheryakova.handling.TaskInfo;
import ru.nsu.meshcheryakova.handling.process.CodeStyleProcess;
import ru.nsu.meshcheryakova.handling.process.CompileProcess;
import ru.nsu.meshcheryakova.handling.process.JavadocProcess;
import ru.nsu.meshcheryakova.handling.process.Process;
import ru.nsu.meshcheryakova.handling.process.TestsProcess;

/**
 * Main class of the application.
 * The application downloads the repositories of the students specified in the config,
 * starts the compilation process, generates documentation, runs tests and checks the code style.
 * The results are in the file 'src/main/resources/report.html'.
 */
public class App {
    private static HashMap<Group, HashMap<Student, StudentInfo>> groupsInfo = new HashMap<>();
    private static HashMap<Task, HashMap<Student, TaskInfo>> tasksInfo = new HashMap<>();

    /**
     * Main method.
     *
     * @param args arguments.
     */
    public static void main(String[] args) throws IOException, NoSuchFieldException,
            InvocationTargetException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InterruptedException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        String fileName = "src/main/resources/configuration.groovy";
        DelegatingScript script = (DelegatingScript) sh.parse(new File(fileName));
        Course config = new Course();
        script.setDelegate(config);
        script.run();
        config.postProcess();

        for (Group group : config.getGroups()) {
            HashMap<Student, StudentInfo> groupInfo = new HashMap<>();
            for (Student student : group.getStudents()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setGradeSettings(config.getGradeSettings());
                File studentDir = new File(String.format("%s/labs/%s/%s",
                        System.getProperty("user.dir"), group.getNumber(),
                        student.getUsername()));
                deleteDirectory(studentDir);
                if (downloadRepo(student, group.getNumber())) { // download repository
                    for (Task task : student.getAssignments()) {
                        Process compileProcess = new CompileProcess();
                        Process javadocProcess = new JavadocProcess();
                        Process testsProcess = new TestsProcess();
                        Process codeStyleProcess = new CodeStyleProcess();
                        List<Process> processes = new ArrayList<>();
                        Collections.addAll(processes, compileProcess, javadocProcess,
                                testsProcess, codeStyleProcess);

                        TaskInfo taskInfo = new TaskInfo();
                        String projectPath = studentDir + "/" + task.getId();

                        for (Process pr : processes) {
                            pr.run(projectPath, taskInfo);
                        }

                        if (taskInfo.isBuild()
                                && taskInfo.isDocumentation()
                                && taskInfo.isCheckStyle()
                                && taskInfo.isRunningTests()
                                && taskInfo.getTests().getAll() == taskInfo.getTests().getPassed()) {
                            taskInfo.setPoints(1);
                            studentInfo.addTask(task, 1);
                        } else {
                            taskInfo.setPoints(0);
                            studentInfo.addTask(task, 0);
                        }

                        if (tasksInfo.containsKey(task)) {
                            HashMap<Student, TaskInfo> temp = tasksInfo.get(task);
                            temp.put(student, taskInfo);
                            tasksInfo.replace(task, temp);
                        } else {
                            HashMap<Student, TaskInfo> temp = new HashMap<>();
                            temp.put(student, taskInfo);
                            tasksInfo.put(task, temp);
                        }
                    }
                }
                groupInfo.put(student, studentInfo);
            }
            groupsInfo.put(group, groupInfo);
        }
        HTMLResults.makeReport(groupsInfo, tasksInfo, config);
    }

    /**
     * Method for deleting directory with files inside.
     *
     * @param path directory path.
     */
    private static void deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        path.delete();
    }

    /**
     * Method for downloading repository.
     *
     * @param student student.
     * @param number  number of group.
     * @return successful download or not.
     */
    private static boolean downloadRepo(Student student, String number) throws IOException,
            InterruptedException {
        java.lang.Process process = Runtime.getRuntime().exec(String.format("git clone %s ./labs/%s/%s",
                student.getRepository(), number, student.getUsername()));
        int exitCode = process.waitFor();
        return exitCode == 0;
    }
}