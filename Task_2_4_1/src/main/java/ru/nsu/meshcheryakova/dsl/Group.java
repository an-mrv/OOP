package ru.nsu.meshcheryakova.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Group.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Group extends GroovyConfigurable {
    private String number;
    private List<Student> students;
}
