import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */

public class Tests {

    @Test
    public void myGrade() {
        RecordBook rb = new RecordBook();
        rb.putGrade(1, "введение в алгебру и анализ", "отлично");
        rb.putGrade(1, "введение в дискретную математику и математическую логику",
                "хорошо");
        rb.putGrade(1, "декларативное программирование", "отлично");
        rb.putGrade(1, "история", "отлично");
        rb.putGrade(1, "императивное программирование", "хорошо");
        rb.putGrade(1, "основы культуры речи", "отлично");

        rb.putGrade(2, "введение в алгебру и анализ", "отлично");
        rb.putGrade(2, "цифровые платформы", "отлично");
        rb.putGrade(2, "введение в дискретную математику и математическую логику",
                "хорошо");
        rb.putGrade(2, "декларативное программирование", "отлично");
        rb.putGrade(2, "иностранный язык", "отлично");
        rb.putGrade(2, "императивное программирование", "хорошо");

        String currentAverageScore = String.format("%.1f", rb.giveCurrentAverageScore());
        assert (currentAverageScore.equals("4,7"));
        assert (rb.givePossibilityGetRedDiploma());
        assert (!rb.givePossibilityGetIncreasedScholarship(2));
    }

    @Test
    public void correctionOfMarks() {
        RecordBook rb = new RecordBook();
        rb.putGrade(1, "введение в алгебру и анализ", "удовлетворительно");
        rb.putGrade(1, "введение в дискретную математику и математическую логику",
                "хорошо");
        rb.putGrade(1, "декларативное программирование", "хорошо");
        rb.putGrade(1, "история", "отлично");
        rb.putGrade(1, "императивное программирование", "хорошо");
        rb.putGrade(1, "основы культуры речи", "отлично");
        String currentAverageScore = String.format("%.1f", rb.giveCurrentAverageScore());
        assert (currentAverageScore.equals("4,2"));
        assert (!rb.givePossibilityGetRedDiploma());
        assert (!rb.givePossibilityGetIncreasedScholarship(1));

        rb.putGrade(1, "введение в алгебру и анализ", "отлично");
        currentAverageScore = String.format("%.1f", rb.giveCurrentAverageScore());
        assert (currentAverageScore.equals("4,5"));
        assert (!rb.givePossibilityGetRedDiploma());
        assert (!rb.givePossibilityGetIncreasedScholarship(1));

        rb.putGrade(1, "введение в дискретную математику и математическую логику",
                "отлично");
        rb.putGrade(1, "декларативное программирование", "отлично");
        rb.putGrade(1, "императивное программирование", "отлично");
        currentAverageScore = String.format("%.1f", rb.giveCurrentAverageScore());
        assert (currentAverageScore.equals("5,0"));
        assert (rb.givePossibilityGetRedDiploma());
        assert (rb.givePossibilityGetIncreasedScholarship(1));

        rb.putGrade(8, "выполнение и защита выпускной квалификационной работы",
                "хорошо");
        assert (!rb.givePossibilityGetRedDiploma());
    }

    @Test
    public void recordBookWithAllSemesters() {
        RecordBook rb = new RecordBook();
        rb.putGrade(1, "введение в алгебру и анализ", "отлично");
        rb.putGrade(1, "введение в дискретную математику и математическую логику",
                "хорошо");
        rb.putGrade(1, "история", "отлично");

        rb.putGrade(2, "введение в алгебру и анализ", "отлично");
        rb.putGrade(2, "цифровые платформы", "отлично");
        rb.putGrade(2, "введение в дискретную математику и математическую логику",
                "отлично");
        rb.putGrade(2, "иностранный язык", "отлично");

        rb.putGrade(3, "введение в искусственный интеллект", "отлично");
        rb.putGrade(3, "операционные системы", "отлично");
        rb.putGrade(3, "иностранный язык", "хорошо");
        rb.putGrade(3, "объектно-ориентированное программирование", "отлично");

        rb.putGrade(4, "теория параллелизма", "отлично");
        rb.putGrade(4, "введение в аналоговую электронику и технику измерений",
                "отлично");
        rb.putGrade(4, "деловой английский язык", "хорошо");
        rb.putGrade(4, "объектно-ориентированное программирование", "отлично");

        rb.putGrade(5, "введение в разработку мобильных приложений", "отлично");
        rb.putGrade(5, "вычислительная математика", "отлично");
        rb.putGrade(5, "методы машинного обучения", "хорошо");
        rb.putGrade(5, "кибербезопасность", "отлично");

        rb.putGrade(6, "хранение и обработка информации", "отлично");
        rb.putGrade(6, "проектирование программного обеспечения", "отлично");
        rb.putGrade(6, "компьютерное моделирование", "хорошо");
        rb.putGrade(6, "допустимые множества и вычислимость", "отлично");

        rb.putGrade(7, "инновационная экономика и технологическое предпринимательство",
                "отлично");
        rb.putGrade(7, "защита информации", "хорошо");
        rb.putGrade(7, "распределенные алгоритмы", "отлично");

        rb.putGrade(8, "выполнение и защита выпускной квалификационной работы",
                "отлично");
        rb.putGrade(8, "экономика", "хорошо");
        rb.putGrade(8, "производственная практика", "отлично");

        String currentAverageScore = String.format("%.1f", rb.giveCurrentAverageScore());
        assert (currentAverageScore.equals("4,8"));
        assert (rb.givePossibilityGetRedDiploma());
        assert (rb.givePossibilityGetIncreasedScholarship(2));

        rb.putGrade(8, "выполнение и защита выпускной квалификационной работы",
                "хорошо");
        assert (!rb.givePossibilityGetRedDiploma());

        rb.putGrade(6, "допустимые множества и вычислимость", "удовлетворительно");
        rb.putGrade(4, "введение в аналоговую электронику и технику измерений",
                "удовлетворительно");
        rb.putGrade(7, "распределенные алгоритмы", "хорошо");
        currentAverageScore = String.format("%.1f", rb.giveCurrentAverageScore());
        assert (currentAverageScore.equals("4,6"));
        assert (!rb.givePossibilityGetRedDiploma());
        assert (!rb.givePossibilityGetIncreasedScholarship(7));
    }

    @Test
    public void invalidMark() {
        RecordBook rb = new RecordBook();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            rb.putGrade(1, "введение в алгебру и анализ", "5");
        });
    }
}