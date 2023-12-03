import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests.
 */

public class Tests {

    @Test
    public void testFromTask() {
        Expression exp = new Expression("sin + - 1 2 1");
        try {
            assertTrue(exp.calculate() - 0.0 < 0.000001);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void hardTests() {
        Expression exp1 = new Expression("+ - sqrt 64 * 15 4 cos / 20 4");
        try {
            assertTrue(exp1.calculate() - (-51.71633) < 0.000001);
        } catch (Exception e) {
            fail();
        }

        Expression exp2 = new Expression("pow log 2.0 4.0 * 1.5 4.0");
        try {
            assertTrue(exp2.calculate() - 64.0 < 0.000001);
        } catch (Exception e) {
            fail();
        }

        Expression exp3 = new Expression("- + sqrt 169 / 1.44 1.2");
        try {
            assertTrue(exp3.calculate() - (-14.2) < 0.000001);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testWrongArgumentException() {
        Expression exp = new Expression("sinn + - 1 2 1");
        Assertions.assertThrows(WrongArgumentException.class, exp::calculate);
    }

    @Test
    public void WrongPrefixNotationException() {
        Expression exp1 = new Expression("* sin + - 1 2 1");
        Assertions.assertThrows(WrongPrefixNotationException.class, exp1::calculate);

        Expression exp2 = new Expression("sin + - 1 2 5 3 2 1");
        Assertions.assertThrows(WrongPrefixNotationException.class, exp2::calculate);

        Expression exp3 = new Expression("sin + - 1 2 1 sqrt");
        Assertions.assertThrows(WrongPrefixNotationException.class, exp3::calculate);
    }
}
