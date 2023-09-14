import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

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
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 8});
        if (!p1.minus(p2).toString().equals("7x^3 - 2x^2 + x + 1")) {
            fail();
        }
        if (!p1.differentiate(2).toString().equals("42x + 12")) {
            fail();
        }
        if (!p1.differentiate(3).toString().equals("42")) {
            fail();
        }
        Polynomial p3 = new Polynomial(new int[] {4, 3, 6, 7});
        if (!p3.equality(p1)) {
            fail();
        }
    }

    @Test
    void test3() {
        Polynomial p1 = new Polynomial(new int[] {-5, 2, 8, -3, -3, 0, 1, 0, 1});
        Polynomial p2 = new Polynomial(new int[] {21, -9, -4, 0, 5, 0, 3});
        if (!p1.times(p2).toString().equals("3x^14 + 8x^12 − 8x^10 − 18x^9 + 26x^8 − 18x^7 + 58x^6 + 49x^5 − 93x^4 − 143x^3 + 170x^2 + 87x−105")) {
            fail();
        }
        if (p1.times(p2).evaluate(0) != -105) {
            fail();
        }
        Polynomial p3 = new Polynomial(new int[] {21, -9, -4, 0, 5, 0, 3});
        if (!p2.equality(p3)) {
            fail();
        }
    }
}