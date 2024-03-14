import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Bad client class for testing.
 */
public class BadClient {
    private Socket socket;

    /**
     * Connecting to the server without interaction.
     */
    public void start() {
        try {
            socket = new Socket("localhost", 8080);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            List<Integer> numbers = new ArrayList<>();

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
