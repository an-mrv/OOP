package ru.nsu.meshcheryakova.handling;

import java.util.HashMap;
import lombok.Data;
import ru.nsu.meshcheryakova.dsl.GradeSettings;
import ru.nsu.meshcheryakova.dsl.Task;

/**
 * Class for storing information about student.
 */
@Data
public class StudentInfo {
    private HashMap<Task, Integer> tasks = new HashMap<>();
    private Integer sumOfPoints = 0;
    private Integer finalGrade = 2;
    private GradeSettings gradeSettings;

    /**
     * Method for adding completed task with points for it.
     *
     * @param task completed task.
     * @param point points.
     */
    public void addTask(Task task, int point) {
        tasks.put(task, point);
        sumOfPoints += point;
        checkFinalGrade();
    }

    /**
     * Method for updating the final grade.
     */
    private void checkFinalGrade() {
        if (sumOfPoints >= gradeSettings.getMinimumPointsForFive()) {
            finalGrade = 5;
        } else if (sumOfPoints >= gradeSettings.getMinimumPointsForFour()) {
            finalGrade = 4;
        } else if (sumOfPoints >= gradeSettings.getMinimumPointsForThree()) {
            finalGrade = 3;
        } else {
            finalGrade = 2;
        }
    }
}
