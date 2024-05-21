package ru.nsu.meshcheryakova.handling;

import lombok.Data;

/**
 * Class for storing information about tests.
 */
@Data
public class TestsInfo {
    private int all = 0;
    private int passed = 0;
    private int failed = 0;
    private int notCompleted = 0;
}
