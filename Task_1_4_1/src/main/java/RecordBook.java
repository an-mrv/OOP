import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that implements the work of the student's electronic record book.
 */
public class RecordBook {
    private Integer amountOfAllGrades;
    private Integer sumOfAllGrades;
    private Integer amountOfThrees;
    private HashMap<String, ArrayList<Integer>> finalGrades;
    private HashMap<Integer, HashMap<String, Integer>> grades;

    /**
     * Constructor.
     */
    public RecordBook() {
        this.amountOfAllGrades = 0;
        this.sumOfAllGrades = 0;
        this.amountOfThrees = 0;
        this.grades = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            this.grades.put(i + 1, new HashMap<>());
        }
        this.finalGrades = new HashMap<>();
    }

    /**
     * Converting a grade from a string to a number.
     *
     * @param grade Grade as string
     * @return Grade as number
     */
    private int convertGrade(String grade) {
        if (grade.equals(new String("удовлетворительно".getBytes(), StandardCharsets.UTF_8))) {
            return 3;
        } else if (grade.equals(new String("хорошо".getBytes(), StandardCharsets.UTF_8))) {
            return 4;
        } else if (grade.equals(new String("отлично".getBytes(), StandardCharsets.UTF_8))) {
            return 5;
        } else {
            return 0;
        }
    }

    /**
     * Function for putting the grade into the record book.
     * If the grade for a particular subject in the semester has already been set, then it can be changed.
     *
     * @param semester semester number
     * @param subj name of the subject
     * @param mark mark for this subject (valid: "удовлетворительно", "хорошо", "отлично")
     */
    public void putGrade(int semester, String subj, String mark) throws IllegalArgumentException {
        String mark2 = new String(mark.getBytes(), StandardCharsets.UTF_8);
        int grade = convertGrade(mark2);
        String subject = new String(subj.getBytes(), StandardCharsets.UTF_8);
        if (grade == 0) {
            throw new IllegalArgumentException("Invalid mark");
        }

        HashMap<String, Integer> temp = this.grades.get(semester);
        if (temp.containsKey(subject)) { //changing an existing mark
            this.amountOfAllGrades--;
            this.sumOfAllGrades -= temp.get(subject);
            if (temp.get(subject) == 3) {
                this.amountOfThrees--;
            }
        }
        temp.put(subject, grade);
        this.grades.put(semester, temp);

        if (grade == 3) {
            this.amountOfThrees++;
        }

        this.amountOfAllGrades++;
        this.sumOfAllGrades += grade;

        if (this.finalGrades.containsKey(subject)) {
            ArrayList<Integer> tmp = this.finalGrades.get(subject);
            if (tmp.get(0) < semester) {
                tmp.add(0, semester);
                tmp.add(1, grade);
            } else if ((tmp.get(0) == semester) && (tmp.get(1) != grade)) {
                tmp.add(1, grade);
            }
        } else {
            ArrayList<Integer> tmp = new ArrayList<>();
            tmp.add(semester);
            tmp.add(grade);
            this.finalGrades.put(subject, tmp);
        }
    }

    /**
     * Function that returns the current average score for the all studying time.
     *
     * @return current average score
     */
    public Double giveCurrentAverageScore() {
        return (double) this.sumOfAllGrades / (double) this.amountOfAllGrades;
    }

    /**
     * Function that returns the possibility of getting an increased scholarship in the specified
     * semester. An increased scholarship is given if all grades in the current semester are 5.
     *
     * @param semester current semester
     * @return true - if the increased scholarship is given; false - if the increased scholarship
     * is not given
     */
    public Boolean givePossibilityGetIncreasedScholarship(int semester) {
        HashMap<String, Integer> temp = this.grades.get(semester);
        for (Map.Entry<String, Integer> entry : temp.entrySet()) {
            if (entry.getValue() != 5) {
                return false;
            }
        }
        return true;
    }

    /**
     * Function that returns the possibility of receiving a red diploma at the moment.
     *
     * @return true - if at the moment there is a possibility; false - if there is no
     * probability at the moment
     */
    public Boolean givePossibilityGetRedDiploma() {
        int amountOfAllFinalGrades = 0;
        int amountOfFives = 0;
        if (this.amountOfThrees == 0) {
            for (Map.Entry<String, ArrayList<Integer>> entry : this.finalGrades.entrySet()) {
                if (entry.getValue().get(1) == 5) {
                    amountOfFives++;
                }
                amountOfAllFinalGrades++;
            }
            double result = (double) amountOfFives / (double) amountOfAllFinalGrades;
            if (result >= 0.75) {
                String subject = new String("выполнение и защита выпускной квалификационной работы".getBytes(),
                        StandardCharsets.UTF_8);
                if (this.grades.get(8).containsKey(subject)) {
                    int mark = this.grades.get(8).get(subject);
                    if (mark == 5) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
