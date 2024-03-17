import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Client class.
 */
public class Client {
    private Socket socket;

    /**
     * Connecting to the server and processing the received list of numbers.
     */
    public void start() {
        try {
            socket = new Socket("localhost", 8080);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            List<Integer> numbers = new ArrayList<>();

            while (true) {
                int size = 0;
                if (in.hasNextInt()) {
                    size = in.nextInt();
                }
                if (size == -1) {
                    in.close();
                    out.close();
                    break;
                } else if (size == 0) {
                    continue;
                }
                for (int i = 0; i < size; i++) {
                    if (in.hasNextInt()) {
                        int n = in.nextInt();
                        numbers.add(n);
                    }
                }
                out.println(Check.hasNotPrime(numbers));
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
