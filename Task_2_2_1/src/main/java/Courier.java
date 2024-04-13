import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for a courier thread.
 */
public class Courier extends Thread {
    private String name;
    private Integer bagCapacity;
    private Pizzeria pizzeria;
    private static Logger log;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT:%1$tL] [%4$-7s] %5$s %n");
        log = Logger.getLogger(Pizzeria.class.getName());
    }

    /**
     * Constructor.
     *
     * @param name courier's name
     * @param bagCapacity the number of pizzas that the courier can put in his bag
     */
    public Courier(String name, Integer bagCapacity) {
        this.name = name;
        this.bagCapacity = bagCapacity;
    }

    /**
     * Set a pizzeria.
     *
     * @param pizzeria pizzeria object
     */
    public void setPizzeria(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        log.info("Courier " +  this.name + " started to work.");
        while (!isInterrupted()) {
            int totalDeliveryTime = 0;
            List<Order> pickedOrders = new ArrayList<>();
            try {
                int amountOfPickedPizzas = this.bagCapacity;
                int count = pizzeria.forDelivery.size();
                while (!pizzeria.isOpen.get()) {
                    if (count < bagCapacity && (count != 0)) {
                        amountOfPickedPizzas = count;
                        break;
                    }
                    count = pizzeria.forDelivery.size();
                }

                for (int i = 0; i < amountOfPickedPizzas; i++) {
                    Order order = pizzeria.forDelivery.get();
                    pickedOrders.add(order);
                    totalDeliveryTime += order.getDeliveryTime();
                }

                StringBuffer message = new StringBuffer("[");
                for (int i = 0; i < pickedOrders.size(); i++) {
                    message.append(pickedOrders.get(i).getId());
                    if (i < (pickedOrders.size() - 1)) {
                        message.append(" ");
                    }
                }
                message.append("]");
                log.info(message + " [were picked by courier " + this.name + "]");
                Thread.sleep(totalDeliveryTime);
                log.info(message + " [were delivered by courier " + this.name + "]");
                pizzeria.deliveredOrders.getAndAdd(pickedOrders.size());
            } catch (InterruptedException e) {
                log.info("Courier " +  this.name + " finished to work.");
            }
        }
    }

}
