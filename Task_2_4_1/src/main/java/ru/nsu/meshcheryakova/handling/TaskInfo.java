package ru.nsu.meshcheryakova.handling;

import lombok.Data;

/**
 * Class for storing information about task.
 */
@Data
public class TaskInfo {
    private boolean build = false;
    private boolean documentation = false;
    private boolean checkStyle = false;
    private boolean runningTests = false;
    private TestsInfo tests;
    private Integer points = 0;
}
