package ru.nsu.meshcheryakova.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Grade settings. If the settings are not specified in the config, then by default:
 * for 5 you need 8+ points, for 4 - 6+, for 3 - 4+.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GradeSettings extends GroovyConfigurable {
    private int minimumPointsForFive = 8;
    private int minimumPointsForFour = 6;
    private int minimumPointsForThree = 4;
}
