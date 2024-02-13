import java.util.List;

/**
 * Sequential solution.
 */
public class SequentialExecution {

    /**
     * The main method for checking the presence of a not prime number.
     *
     * @param list list of numbers to check
     * @return the presence or absence of at least one not prime number in the list
     */
    public static Boolean hasNotPrime(List<Integer> list) {
        for (Integer num : list) {
            if (isNotPrime(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checking whether a number is prime.
     *
     * @param num the number
     * @return is the number prime or not
     */
    public static Boolean isNotPrime (Integer num) {
        if (num == 0 || num == 1) {
            return true;
        }
        if (num % 2 == 0) {
            return true;
        }
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }
}
