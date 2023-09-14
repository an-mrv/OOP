import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Tests for the task_1_1_2.
 */

public class Tests {

    @Test
    void test1() {
        Polynomial p1 = new Polynomial(new int[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        if (!p1.plus(p2.differentiate(1)).toString().equals("7x^3 + 6x^2 + 19x + 6")) {
            fail();
        }
        if (p1.times(p2).evaluate(2) != 3510) {
            fail();
        }
    }

    @Test
    void test2() {
        Polynomial p1 = new Polynomial(new int[] {-5, 2, 8, -3, -3, 0, 1, 0, 2});
        Polynomial p2 = new Polynomial(new int[] {21, -9, -4, 0, 5, 0, 3});
        if (!p1.plus(p2).toString().equals(
                "2x^8 + 4x^6 + 2x^4 - 3x^3 + 4x^2 - 7x + 16")) {
            fail();
        }
        if (!p1.minus(p2).toString().equals(
                "2x^8 - 2x^6 - 8x^4 - 3x^3 + 12x^2 + 11x - 26")) {
            fail();
        }
        if (!p1.times(p2).toString().equals(
                "6x^14 + 13x^12 - 12x^10 - 27x^9 + 47x^8 - 18x^7 + "
                        + "58x^6 + 49x^5 - 93x^4 - 143x^3 + 170x^2 + 87x - 105")) {
            fail();
        }
    }

    @Test
    void test3() {
        Polynomial p = new Polynomial(new int[] {-105, 87, 170, 0, -93, 9});
        if (p.evaluate(0) != -105) {
            fail();
        }
        if (p.evaluate(2) != -451) {
            fail();
        }
    }

    @Test
    void test4() {
        Polynomial p = new Polynomial(new int[] {4, 3, 6, 7});
        if (!p.differentiate(2).toString().equals("42x + 12")) {
            fail();
        }
        if (!p.differentiate(3).toString().equals("42")) {
            fail();
        }
    }

    @Test
    void test5() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        Polynomial p3 = new Polynomial(new int[] {4, 3, 6, 7});
        if (p1.equality(p2)) {
            fail();
        }
        if (!p3.equality(p1)) {
            fail();
        }
    }
}