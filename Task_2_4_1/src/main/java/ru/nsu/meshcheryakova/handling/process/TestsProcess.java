package ru.nsu.meshcheryakova.handling.process;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.SneakyThrows;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.nsu.meshcheryakova.handling.TaskInfo;
import ru.nsu.meshcheryakova.handling.TestsInfo;

/**
 * Class for running tests.
 */
public class TestsProcess implements Process {
    /**
     * Run tests process.
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
                    .forTasks("test")
                    .run();
        } catch (BuildException ignored) {
        } finally {
            connection.close();
        }

        TestsInfo testsInfo = parseResults(projectPath);
        if (testsInfo != null) {
            taskInfo.setRunningTests(true);
            taskInfo.setTests(testsInfo);
        }
    }

    /**
     * Parse results of the test process.
     *
     * @param projectPath the path to the project.
     * @return information about tests.
     */
    @SneakyThrows
    private TestsInfo parseResults(String projectPath) {
        File testResultsPath = new File(String.format("%s/build/test-results/test", projectPath));
        if (testResultsPath.exists()) {
            File[] files = testResultsPath.listFiles((dir, name) -> name.endsWith(".xml"));
            int allTests = 0;
            int failed = 0;
            int notCompleted = 0;
            for (File file : files) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                builder = factory.newDocumentBuilder();
                Document document = null;
                document = builder.parse(file);
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
