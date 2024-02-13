import java.util.List;

/**
 * Parallel solution using parallelStream().
 */
public class ParallelExecution {
    /**
     * The main method for checking the presence of a not prime number.
     *
     * @param list list of numbers to check
     * @return the presence or absence of at least one not prime number in the list
     */
    public static boolean hasNotPrime(List<Integer> list) {
        return list.parallelStream().anyMatch(SequentialExecution::isNotPrime);
    }
}
