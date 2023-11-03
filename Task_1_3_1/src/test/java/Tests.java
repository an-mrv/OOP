import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests.
 */

public class Tests {

    @Test
    public void testInput1() {
        SubstringFinder s = new SubstringFinder();
        ArrayList<Integer> res = s.find("src/test/java/input1.txt", "бра");
        ArrayList<Integer> answ1 = new ArrayList<>(List.of(1, 8));
        assertEquals(answ1, res);

        res = s.find("src/test/java/input1.txt", "аб");
        ArrayList<Integer> answ2 = new ArrayList<>(List.of(0, 7));
        assertEquals(answ2, res);

        res = s.find("src/test/java/input1.txt", "а");
        ArrayList<Integer> answ3 = new ArrayList<>(List.of(0, 3, 5, 7, 10));
        assertEquals(answ3, res);

        res = s.find("src/test/java/input1.txt", "");
        ArrayList<Integer> answ4 = new ArrayList<>();
        assertEquals(answ4, res);

        res = s.find("src/test/java/input1.txt", "абрр");
        assertEquals(answ4, res);
    }

    /**
     * Big file.
     */
    @Test
    public void testInput2() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/java/input2.txt"));
            for (int i = 0; i < 128; i++) {
                writer.write("acca");
            }
            writer.write("bacbab");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SubstringFinder s = new SubstringFinder();
        ArrayList<Integer> res = s.find("src/test/java/input2.txt", "ab");
        ArrayList<Integer> answ = new ArrayList<>(List.of(511, 516));
        assertEquals(answ, res);
    }

    /**
     * Empty file.
     */
    @Test
    public void testInput3() {
        SubstringFinder s = new SubstringFinder();
        ArrayList<Integer> res = s.find("src/test/java/input3.txt", "a");
        ArrayList<Integer> answ = new ArrayList<>();
        assertEquals(answ, res);
    }

    /**
     * Nonexistent file.
     */
    @Test
    public void testInput4() {
        SubstringFinder s = new SubstringFinder();
        RuntimeException thrown =
                Assertions.assertThrows(RuntimeException.class, () -> {
                    ArrayList<Integer> res = s.find("src/test/java/input4.txt", "a");
                });
    }
}
