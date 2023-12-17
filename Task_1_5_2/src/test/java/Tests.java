import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for Task_1_5_2.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    @Order(1)
    public void testAddAndShow() throws org.apache.commons.cli.ParseException, ParseException,
            IOException {
        Main.main(new String[]{"-add", "My note1", "Do something"});
        Main.main(new String[]{"-add", "Important!", "Some important things"});
        Main.main(new String[]{"-add", "My note2", "Very important note"});
        Main.main(new String[]{"-add", "My note3", "Very important note2"});
        Main.main(new String[]{"-add", "my tasks", "Some tasks"});
        Main.main(new String[]{"-show"});
        assertEquals("My note1: Do something\n"
                + "Important!: Some important things\n"
                + "My note2: Very important note\n"
                + "My note3: Very important note2\n"
                + "my tasks: Some tasks\n", output.toString());
    }

    @Test
    @Order(2)
    public void testDeleteAndShow() throws FileNotFoundException,
            org.apache.commons.cli.ParseException, ParseException {
        Main.main(new String[]{"-rm", "My note3"});
        Main.main(new String[]{"-show"});
        assertEquals("My note1: Do something\n"
                + "Important!: Some important things\n"
                + "My note2: Very important note\n"
                + "my tasks: Some tasks\n", output.toString());
    }

    @Test
    @Order(3)
    public void testSelectOneWord() throws FileNotFoundException,
            org.apache.commons.cli.ParseException, ParseException {
        Main.main(new String[]{"-select", "15.12.2023 15:00", "31.12.2023 21:00", "my"});
        assertEquals("My note1: Do something\n"
                + "My note2: Very important note\n"
                + "my tasks: Some tasks\n", output.toString());
    }

    @Test
    @Order(4)
    public void testSelectSeveralWords() throws FileNotFoundException,
            org.apache.commons.cli.ParseException, ParseException {
        Main.main(new String[]{"-select", "15.12.2023 15:00", "31.12.2023 21:00", "My", "nOte"});

        assertEquals("My note1: Do something\n"
                + "My note2: Very important note\n", output.toString());
    }

    @Test
    @Order(5)
    public void testDeleteNonExistentNote() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Main.main(new String[]{"-rm", "a non-existent note"});
        });
    }

    @Test
    @Order(6)
    public void testNoSuitableNotes() throws FileNotFoundException,
            org.apache.commons.cli.ParseException, ParseException {
        Main.main(new String[]{"-select", "15.12.2023 15:00", "31.12.2023 21:00", "My",
                "note", "important"});

        assertEquals("", output.toString());
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }
}
