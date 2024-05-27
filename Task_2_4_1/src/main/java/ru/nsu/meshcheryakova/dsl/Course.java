package ru.nsu.meshcheryakova.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Course.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Course extends GroovyConfigurable {
    private List<Task> tasks;
    private List<Group> groups;
    private GradeSettings gradeSettings;
}
