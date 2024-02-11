import java.util.ArrayList;
import java.util.List;

/**
 * Class for comparing the execution time of a program.
 */
public class Main {
    /**
     * Comparison.
     */
    public static void main() throws InterruptedException, IllegalArgumentException {
        List<Integer> list = new ArrayList<>();

        for (int i = 2; i < 10000000; i++) {
            if (!SequentialExecution.isNotPrime(i)) {
                list.add(i);
            }
        }

        long start;
        long end;

        start = System.currentTimeMillis();
        SequentialExecution.hasNotPrime(list);
        end = System.currentTimeMillis();
        System.out.println("Sequential execution: " + (end - start));

        for (int i = 1; i < 5; i++) {
            start = System.currentTimeMillis();
            ThreadsExecution.hasNotPrime(list, i);
            end = System.currentTimeMillis();
            System.out.printf("Thread execution with %d thread: %d\n", i, end - start);
        }

        start = System.currentTimeMillis();
        ParallelExecution.hasNotPrime(list);
        end = System.currentTimeMillis();
        System.out.println("Parallel execution: " + (end - start));
    }
}
