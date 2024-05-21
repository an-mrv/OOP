package ru.nsu.meshcheryakova;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.groovy.groovysh.Main;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ru.nsu.meshcheryakova.dsl.Course;
import ru.nsu.meshcheryakova.dsl.Group;
import ru.nsu.meshcheryakova.dsl.Student;
import ru.nsu.meshcheryakova.dsl.Task;
import ru.nsu.meshcheryakova.handling.HTMLResults;
import ru.nsu.meshcheryakova.handling.StudentInfo;
import ru.nsu.meshcheryakova.handling.TaskInfo;
import ru.nsu.meshcheryakova.handling.TestsInfo;

import static com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions.NONE;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

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
            NoSuchMethodException, InterruptedException, ParserConfigurationException,
            SAXException, CheckstyleException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        String fileName = "src/main/resources/configuration.groovy";
        DelegatingScript script = (DelegatingScript)sh.parse(new File(fileName));
        Course config = new Course();
        script.setDelegate(config);
        script.run();
        config.postProcess();

        for (Group group : config.getGroups()) {
            HashMap<Student, StudentInfo> groupInfo = new HashMap<>();
            for (Student student : group.getStudents()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setGradeSettings(config.getGradeSettings());
                File studentDir = new File(String.format("%s/labs/%s/%s", System.getProperty("user.dir"),
                        group.getNumber(), student.getUsername()));
                deleteDirectory(studentDir);
                if (downloadRepo(student, group.getNumber())) { // download repository
                    for (Task task : student.getAssignments()) {
                        TaskInfo taskInfo = new TaskInfo();
                        String projectPath = String.format("%s/labs/%s/%s/%s",
                                System.getProperty("user.dir"), group.getNumber(),
                                student.getUsername(), task.getId());
                        if (compile(projectPath)) { // compilation process
                            taskInfo.setBuild(true);
                            if (javadoc(projectPath)) { // generate documentation
                                taskInfo.setDocumentation(true);
                                TestsInfo testInfo = tests(projectPath); // run tests
                                if (testInfo != null) {
                                    taskInfo.setRunningTests(true);
                                    taskInfo.setTests(testInfo);
                                    if (testInfo.getAll() == testInfo.getPassed()) {
                                        taskInfo.setPoints(1);
                                        studentInfo.addTask(task, 1);
                                    } else {
                                        taskInfo.setPoints(0);
                                        studentInfo.addTask(task, 0);
                                    }
                                    if (checkStyle(String.format("%s/labs/%s/%s", // check code style
                                            System.getProperty("user.dir"), group.getNumber(),
                                            student.getUsername()), task.getId())) {
                                        taskInfo.setCheckStyle(true);
                                    }
                                }
                            }
                        }
                        if (!taskInfo.isBuild() || !taskInfo.isDocumentation() ||
                                !taskInfo.isRunningTests()) {
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
     * Method for checking code style.
     *
     * @param projectPath the path to the project to check.
     * @param task task to check.
     * @return has the check been passed or not.
     */
    static private boolean checkStyle(String projectPath, String task) throws IOException,
            CheckstyleException {
        DefaultConfiguration config = (DefaultConfiguration) ConfigurationLoader.
                loadConfiguration(String.format("%s/.github/google_checks.xml", projectPath),
                        new PropertiesExpander(new Properties()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(byteArrayOutputStream, NONE);

        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(config);
        checker.addListener(listener);

        List<File> javaFiles = new ArrayList<>();
        Files.walk(Paths.get(String.format("%s/%s/src", projectPath, task)), FOLLOW_LINKS)
                .forEach(file -> {
                    if(file.toFile().isFile() && file.toFile().getPath().endsWith(".java")){
                        File javaFile = file.toFile();
                        javaFiles.add(javaFile);
                    }
                });

        checker.process(javaFiles);
        checker.destroy();

        String results = byteArrayOutputStream.toString();
        return StringUtils.countMatches(results, "[WARN]") <= 5; // 5 warnings are allowed
    }

    /**
     * Method for deleting directory with files inside.
     *
     * @param path directory path.
     */
    static private void deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
    }

    /**
     * Method for downloading repository.
     *
     * @param student student.
     * @param number number of group.
     * @return successful download or not.
     */
    private static boolean downloadRepo(Student student, String number) throws IOException,
            InterruptedException {
        Process process = Runtime.getRuntime().exec(String.format("git clone %s ./labs/%s/%s",
                student.getRepository(), number, student.getUsername()));
        int exitCode = process.waitFor();
        return exitCode == 0;
    }

    /**
     * Method for starting compilation process.
     *
     * @param projectPath the path to the project to compile.
     * @return successful compilation or not.
     */
    private static boolean compile(String projectPath) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(projectPath));
        ProjectConnection connection = connector.connect();
        try {
            connection.newBuild()
                    .forTasks("compileJava")
                    .run();
        } catch (BuildException e) {
            return false;
        }
        finally {
            connection.close();
        }
        return true;
    }

    /**
     * Method for generating documentation.
     *
     * @param projectPath the path to the project to generate documentation.
     * @return successful generating or not.
     */
    public static boolean javadoc(String projectPath) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(projectPath));
        ProjectConnection connection = connector.connect();
        try {
            connection.newBuild()
                    .forTasks("javadoc")
                    .run();
        } catch (BuildException e) {
            return false;
        }
        finally {
            connection.close();
        }
        return new File(String.format("%s/build/docs/javadoc/", projectPath)).exists();
    }

    /**
     * Method for running tests.
     *
     * @param projectPath the path to the project to run tests.
     * @return information about the number of passed, failed and not completed tests.
     */
    public static TestsInfo tests(String projectPath) throws ParserConfigurationException,
            IOException, SAXException {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(projectPath));
        ProjectConnection connection = connector.connect();
        try {
            connection.newBuild()
                    .forTasks("test")
                    .run();
        } catch (BuildException e) {
        } finally {
            connection.close();
        }

        File testResultsPath = new File(String.format("%s/build/test-results/test", projectPath));
        if (testResultsPath.exists()) {
            File[] files = testResultsPath.listFiles((dir, name) -> name.endsWith(".xml"));
            int allTests = 0;
            int failed = 0;
            int notCompleted = 0;
            for (File file : files) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(file);
                Node elem = document.getElementsByTagName("testsuite").item(0);
                NamedNodeMap attributes = elem.getAttributes();
                allTests += Integer.parseInt(attributes.getNamedItem("tests").getNodeValue());
                failed += Integer.parseInt(attributes.getNamedItem("failures").getNodeValue());
                notCompleted += Integer.parseInt(attributes.getNamedItem("skipped").getNodeValue());
            }
            int passed = allTests - failed - notCompleted;
            TestsInfo testsInfo = new TestsInfo();
            testsInfo.setAll(allTests);
            testsInfo.setPassed(passed);
            testsInfo.setFailed(failed);
            testsInfo.setNotCompleted(notCompleted);
            return testsInfo;
        }
        return null;
    }
}
