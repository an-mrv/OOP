/**
 * Class foa an order.
 */
public class Order {
    private int id;
    private String address;
    private Integer deliveryTime;

    /**
     * Constructor.
     *
     * @param address delivery address
     * @param deliveryTime delivery time
     */
    public Order(String address, int deliveryTime) {
        this.address = address;
        this.deliveryTime = deliveryTime;
    }

    /**
     * Get the order ID.
     *
     * @return the order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get the delivery time.
     *
     * @return the delivery time
     */
    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Get the delivery address.
     *
     * @return the delivery address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the order ID.
     *
     * @param id the order ID.
     */
    public void setId(int id) {
        this.id = id;
    }
}
