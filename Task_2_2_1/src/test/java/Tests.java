import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

/**
 * Tests for Task_2_2_1.
 */
public class Tests {
    @Test
    void test() throws IOException, ParseException, InterruptedException {
        List<Order> orders = ReadFiles.ordersRead("src/test/resources/orders.json");
        Pizzeria pizzeria = ReadFiles.pizzeriaRead("src/test/resources/pizzeria.json");
        var pizzeriaThread = new Thread(() -> {
            try {
                pizzeria.workingDay();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var clientThread = new Thread(() -> {
            try {
                new Client(pizzeria, orders);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        pizzeriaThread.start();
        clientThread.start();
        clientThread.join();
        pizzeriaThread.join();
    }
}
