package ru.nsu.meshcheryakova.handling.process;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import ru.nsu.meshcheryakova.handling.TaskInfo;

import static com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions.NONE;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

/**
 * Class for checking code style.
 */
public class CodeStyleProcess implements Process {
    /**
     * Run code style process.
     *
     * @param projectPath the path to the project.
     * @param taskInfo information about task.
     */
    @SneakyThrows
    public void run(String projectPath, TaskInfo taskInfo) {
        int lastIndex = projectPath.lastIndexOf("/");
        String pathToRepo = projectPath.substring(0, lastIndex);
        DefaultConfiguration config = (DefaultConfiguration) ConfigurationLoader
                .loadConfiguration(String.format("%s/.github/google_checks.xml", pathToRepo),
                        new PropertiesExpander(new Properties()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(byteArrayOutputStream, NONE);

        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(config);
        checker.addListener(listener);

        List<File> javaFiles = new ArrayList<>();
        Files.walk(Paths.get(String.format("%s/src", projectPath)), FOLLOW_LINKS)
                .forEach(file -> {
                    if (file.toFile().isFile() && file.toFile().getPath().endsWith(".java")) {
                        File javaFile = file.toFile();
                        javaFiles.add(javaFile);
                    }
                });

        checker.process(javaFiles);
        checker.destroy();

        String results = byteArrayOutputStream.toString();
        if (StringUtils.countMatches(results, "[WARN]") <= 5) { // 5 warnings are allowed
            taskInfo.setCheckStyle(true);
        }
    }
}
