import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Parallel solution using java.lang.Thread with the ability to set
 * the number of threads used.
 */
public class ThreadsExecution {
    private static AtomicBoolean hasNotPrime;

    /**
     * Class for a new thread.
     */
    public static class MyThread extends Thread {
        private List<Integer> list;

        /**
         * Constructor.
         *
         * @param numbers list of numbers
         */
        public MyThread(List<Integer> numbers) {
            this.list = new ArrayList<>(numbers);
        }

        /**
         * Method run.
         */
        @Override
        public void run() {
            if (SequentialExecution.hasNotPrime(this.list)) {
                hasNotPrime.set(true);
            }
        }
    }

    /**
     * The main method for checking the presence of a not prime number.
     *
     * @param numbers list of numbers to check
     * @param amount  number of threads
     * @return the presence or absence of at least one not prime number in the list
     */
    public static boolean hasNotPrime(List<Integer> numbers, int amount)
            throws InterruptedException, IllegalArgumentException {
        hasNotPrime = new AtomicBoolean(false);
        if (amount < 1) {
            throw new IllegalArgumentException("Illegal amount of threads!");
        }
        if (amount > numbers.size()) {
            amount = numbers.size();
        }
        MyThread[] threads = new MyThread[amount];
        int lenOfPart = numbers.size() / amount;
        for (int i = 0; i < amount - 1; i++) {
            threads[i] = new MyThread(numbers.subList(i * lenOfPart, (i + 1) * lenOfPart));
        }
        threads[amount - 1] = new MyThread(numbers.subList((amount - 1) * lenOfPart,
                numbers.size()));

        for (int i = 0; i < amount; i++) {
            threads[i].start();
        }

        for (int i = 0; i < amount; i++) {
            threads[i].join();
        }
        return hasNotPrime.get();
    }
}
