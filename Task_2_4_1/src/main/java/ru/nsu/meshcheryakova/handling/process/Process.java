package ru.nsu.meshcheryakova.handling.process;

import ru.nsu.meshcheryakova.handling.TaskInfo;

/**
 * Process.
 */
public interface Process {
    /**
     * Run process.
     *
     * @param projectPath the path to the project.
     * @param taskInfo information about task.
     */
    void run(String projectPath, TaskInfo taskInfo);
}
