package ru.nsu.meshcheryakova.handling.process;

import java.io.File;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import ru.nsu.meshcheryakova.handling.TaskInfo;

/**
 * Class for starting compilation process.
 */
public class CompileProcess implements Process {
    /**
     * Run compilation process.
     *
     * @param projectPath the path to the project.
     * @param taskInfo information about task.
     */
    public void run(String projectPath, TaskInfo taskInfo) {
        GradleConnector connector = GradleConnector.newConnector();
        connector.forProjectDirectory(new File(projectPath));
        ProjectConnection connection = connector.connect();
        try {
            connection.newBuild()
                    .forTasks("compileJava")
                    .run();
        } catch (BuildException e) {
            return;
        } finally {
            connection.close();
        }
        taskInfo.setBuild(true);
    }
}
