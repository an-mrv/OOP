import java.util.List;

/**
 * Class for a client thread.
 */
public class Client extends Thread {
    /**
     * Constructor.
     * The customer sends orders to the pizzeria at a random time.
     *
     * @param pizzeria pizzeria object
     * @param orders list of orders to send
     */
    public Client(Pizzeria pizzeria, List<Order> orders) throws InterruptedException {
        for (Order order : orders) {
            boolean result = pizzeria.sendOrder(order);
            if (!result) {
                break;
            }
            Thread.sleep((int) (Math.random() * 200)+1);
        }
    }

}
