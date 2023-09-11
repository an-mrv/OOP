import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 * Tests for the task_1_1_1
 */

public class Tests {
    @Test
    void test1() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] ans = {1, 2, 3, 4, 5};
        Heapsort.heap_sort(arr);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != ans[i]) {
                fail();
            }
        }
    }

    @Test
    void test2() {
        int[] arr = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            arr[i] = (int) ((Math.random() * 2000000) - 1000000);
        }
        Heapsort.heap_sort(arr);
        for (int i = 1; i < 1000000; i++) {
            if (arr[i - 1] > arr[i]) {
                fail();
            }
        }
    }
}