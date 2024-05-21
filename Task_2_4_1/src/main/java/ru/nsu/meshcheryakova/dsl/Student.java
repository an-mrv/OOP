package ru.nsu.meshcheryakova.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Student.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends GroovyConfigurable {
    private String username;
    private String name;
    private String repository;
    private List<Task> assignments;
}
