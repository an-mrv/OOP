import java.util.logging.Logger;

/**
 * Class for a baker thread.
 */
public class Baker extends Thread {
    private String name;
    private Integer bakingTime;
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
     * @param name baker's name
     * @param bakingTime baking time for one pizza
     */
    public Baker(String name, Integer bakingTime) {
        this.name = name;
        this.bakingTime = bakingTime;
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
        log.info("Baker " +  this.name + " started to work.");
        while (!isInterrupted()) {
            try {
                Order order = pizzeria.forBaking.get();
                log.info("[" + order.getId() + "]" + " [was picked by baker "
                        + this.name + "]");
                Thread.sleep(this.bakingTime);
                log.info("[" + order.getId() + "]" + " [was finished by baker "
                        + this.name + "]");
                pizzeria.forDelivery.add(order);
                log.info("[" + order.getId() + "]" + " [was put by baker "
                        + this.name + " to the storage]");
            } catch (InterruptedException e) {
                log.info("Baker " +  this.name + " finished to work.");
            }
        }
    }
}
