import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Tests.
 */
public class Tests {

    public class ServerThread extends Thread {
        private boolean result;
        private List<Integer> numbers;
        private Integer numberOfClients;

        public ServerThread(List<Integer> numbers, Integer numberOfClients) {
            this.numbers = numbers;
            this.numberOfClients = numberOfClients;
        }

        @Override
        public void run() {
            result = new Server(numbers, numberOfClients).start();
        }
    }

    public class ClientThread extends Thread {
        @Override
        public void run() {
            new Client().start();
        }
    }

    public class BadClientThread extends Thread {
        @Override
        public void run() {
            new BadClient().start();
        }
    }

    @Test
    public void twoClients() {
        List<Integer> numbers = List.of(6, 8, 7, 13, 5, 9, 4);
        ServerThread server = new ServerThread(numbers, 2);
        ClientThread client1 = new ClientThread();
        ClientThread client2 = new ClientThread();

        server.start();
        client1.start();
        client2.start();

        try {
            client1.join();
            client2.join();
            server.join();
            Assertions.assertTrue(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void threeClients() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers, 3);
        ClientThread client1 = new ClientThread();
        ClientThread client2 = new ClientThread();
        ClientThread client3 = new ClientThread();

        server.start();
        client1.start();
        client2.start();
        client3.start();

        try {
            client1.join();
            client2.join();
            client3.join();
            server.join();
            Assertions.assertFalse(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void badClient() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053, 1, 4);
        ServerThread server = new ServerThread(numbers, 2);
        BadClientThread client1 = new BadClientThread();
        ClientThread client2 = new ClientThread();

        server.start();
        client2.start();
        client1.start();

        try {
            client2.join();
            client1.join();
            server.join();
            Assertions.assertTrue(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void zeroClients() {
        List<Integer> numbers = List.of(6, 8, 7, 13, 5, 9, 4);
        ServerThread server = new ServerThread(numbers, 0);

        server.start();

        try {
            server.join();
            Assertions.assertTrue(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
