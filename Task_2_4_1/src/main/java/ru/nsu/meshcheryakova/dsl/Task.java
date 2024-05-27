package ru.nsu.meshcheryakova.dsl;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Task.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends GroovyConfigurable {
    private String id;
    private String name;
    private Integer maxScore;
    private LocalDate softDeadLine;
    private LocalDate hardDeadLine;
}
