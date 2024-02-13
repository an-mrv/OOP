import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests.
 */
public class Tests {
    @Test
    public void test1() throws InterruptedException {
        assertTrue(SequentialExecution.hasNotPrime(List.of(0, 3, 5, 7, 10)));
        assertTrue(ThreadsExecution.hasNotPrime(List.of(0, 3, 5, 7, 10), 3));
        assertTrue(ParallelExecution.hasNotPrime(List.of(0, 3, 5, 7, 10)));
    }

    @Test
    public void test2() throws InterruptedException {
        assertFalse(SequentialExecution.hasNotPrime(List.of(20319251, 6997901, 6997927,
                6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053)));
        assertFalse(ThreadsExecution.hasNotPrime(List.of(20319251, 6997901, 6997927, 6997937,
                17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053), 4));
        assertFalse(ParallelExecution.hasNotPrime(List.of(20319251, 6997901, 6997927,
                6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053)));
    }

    @Test
    public void test3() throws InterruptedException {
        assertTrue(SequentialExecution.hasNotPrime(List.of(3, 5, 7, 13, 23, 10)));
        assertTrue(ThreadsExecution.hasNotPrime(List.of(3, 5, 7, 13, 23, 10), 3));
        assertTrue(ParallelExecution.hasNotPrime(List.of(3, 5, 7, 13, 23, 10)));
    }

    @Test
    public void moreThreadsThanElements() throws InterruptedException {
        assertTrue(ThreadsExecution.hasNotPrime(List.of(3, 5, 7, 13, 23, 10), 8));
    }

    @Test
    public void illegalAmountOfThreads() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ThreadsExecution.hasNotPrime(List.of(3, 5, 7, 13, 23, 10), -1);
        });
    }

    @Test
    public void comparisonOfExecutionTime() throws InterruptedException {
        Main.main();
    }
}
