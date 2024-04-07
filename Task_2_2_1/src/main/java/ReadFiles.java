import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 * Class for reading JSON files.
 */
public class ReadFiles {
    /**
     * Reading JSON files with orders.
     *
     * @param file file name
     * @return list of orders
     */
    public static List<Order> ordersRead(String file) throws IOException, ParseException {
        List<Order> orders = new ArrayList<>();
        Object o = new JSONParser().parse(new FileReader(file));
        JSONObject j = (JSONObject) o;
        JSONArray array = (JSONArray) j.get("orders");
        for (Object obj : array) {
            JSONObject order = (JSONObject) obj;
            String address = (String) order.get("address");
            int deliveryTime = ((Long) order.get("deliveryTime")).intValue();
            orders.add(new Order(address, deliveryTime));
        }
        return orders;
    }

    /**
     * Reading JSON file with pizzeria parameters.
     *
     * @param file file name
     * @return pizzeria object
     */
    public static Pizzeria pizzeriaRead(String file) throws IOException, ParseException {
        List<Baker> bakers = new ArrayList<>();
        List<Courier> couriers = new ArrayList<>();
        Object o = new JSONParser().parse(new FileReader(file));
        JSONObject j = (JSONObject) o;
        JSONArray array = (JSONArray) j.get("bakers");
        for (Object obj : array) {
            JSONObject baker = (JSONObject) obj;
            String name = (String) baker.get("name");
            int bakingTime = ((Long) baker.get("bakingTime")).intValue();
            bakers.add(new Baker(name, bakingTime));
        }

        array = (JSONArray) j.get("couriers");
        for (Object obj : array) {
            JSONObject courier = (JSONObject) obj;
            String name = (String) courier.get("name");
            int bagCapacity = ((Long) courier.get("bagCapacity")).intValue();
            couriers.add(new Courier(name, bagCapacity));
        }

        int storageCapacity = ((Long) j.get("storageCapacity")).intValue();
        int workingDayTime = ((Long) j.get("workingDayTime")).intValue();
        return new Pizzeria(bakers, couriers, storageCapacity, workingDayTime);
    }
}
