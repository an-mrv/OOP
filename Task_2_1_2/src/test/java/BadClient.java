import java.io.IOException;
import java.net.Socket;

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
