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

        public ServerThread(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        public void run() {
            result = new Server(numbers).start();
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
    public void twoGoodClients() {
        List<Integer> numbers = List.of(6, 8, 7, 13, 5, 9, 4);
        ServerThread server = new ServerThread(numbers);
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
    public void threeGoodClients() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
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
    public void oneBadClientAndOneGoodClient() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
        BadClientThread client1 = new BadClientThread();
        ClientThread client2 = new ClientThread();

        server.start();
        client2.start();
        client1.start();

        try {
            client2.join();
            client1.join();
            server.join();
            Assertions.assertFalse(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void zeroClients() {
        List<Integer> numbers = List.of(1, 6, 8, 7, 13, 5, 9, 4);
        ServerThread server = new ServerThread(numbers);

        server.start();

        try {
            server.join();
            Assertions.assertTrue(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void onlyBadClient() {
        List<Integer> numbers = List.of(6, 8, 7, 13, 5, 9, 4);
        ServerThread server = new ServerThread(numbers);
        BadClientThread client = new BadClientThread();

        server.start();
        client.start();

        try {
            client.join();
            server.join();
            Assertions.assertTrue(server.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void threeBadClientsAndOneGoodClient() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
        BadClientThread client1 = new BadClientThread();
        BadClientThread client2 = new BadClientThread();
        BadClientThread client3 = new BadClientThread();
        ClientThread client4 = new ClientThread();

        server.start();
        client1.start();
        client2.start();
        client3.start();
        client4.start();

        try {
            client1.join();
            client2.join();
            client3.join();
            client4.join();
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void threeBadClients() {
        List<Integer> numbers = List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        ServerThread server = new ServerThread(numbers);
        BadClientThread client1 = new BadClientThread();
        BadClientThread client2 = new BadClientThread();
        BadClientThread client3 = new BadClientThread();

        server.start();
        client1.start();
        client2.start();
        client3.start();

        try {
            client1.join();
            client2.join();
            client3.join();
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
