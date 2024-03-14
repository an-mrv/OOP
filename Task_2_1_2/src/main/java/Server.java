import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Server class.
 */
public class Server {
    public AtomicBoolean hasNotPrime = new AtomicBoolean(false);
    private List<Integer> list;
    private List<ServerThread> serverList;
    private int numberOfClients;

    /**
     * Constructor.
     *
     * @param list list for checking
     * @param numberOfClients number of connected clients
     */
    public Server(List<Integer> list, int numberOfClients) {
        this.list = list;
        this.serverList = new ArrayList<>();
        this.numberOfClients = numberOfClients;
    }

    /**
     * Accepting client connections and creating threads to work with each client.
     *
     * @return the presence or absence of at least one not prime number in the list
     */
    public boolean start() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8080);
            if (numberOfClients > list.size()) {
                numberOfClients = list.size();
            } else if (numberOfClients == 0) {
                hasNotPrime.set(Check.hasNotPrime(this.list));
            } else if (numberOfClients < 0) {
                throw new IllegalArgumentException("The number of clients cannot be negative!");
            } else {
                acceptClients(server);
                joinServerThreads();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return hasNotPrime.get();
    }

    /**
     * Accept all client's connections.
     *
     * @param server server socket
     */
    private void acceptClients(ServerSocket server) {
        int lenOfPart = list.size() / numberOfClients;
        Socket socket = null;
        for (int i = 0; i < numberOfClients - 1; i++) {
            try {
                socket = server.accept();
                try {
                    serverList.add(new ServerThread(socket,
                            list.subList(i * lenOfPart, (i + 1) * lenOfPart)));
                } catch (IOException e) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            socket = server.accept();
            try {
                serverList.add(new ServerThread(socket,
                        list.subList((numberOfClients - 1) * lenOfPart, list.size())));
            } catch (IOException e) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waiting for the completion of all server threads.
     */
    private void joinServerThreads() {
        for (int i = 0; i < numberOfClients; i++) {
            try {
                serverList.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Thread for working with the client.
     */
    public class ServerThread extends Thread {
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        private List<Integer> list;

        /**
         * Constructor.
         *
         * @param socket socket for interaction with the client
         * @param list the part of the source list that the client needs to process
         */
        public ServerThread(Socket socket, List<Integer> list) throws IOException {
            this.list = list;
            this.socket = socket;
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream());
            start();
        }

        /**
         * Run the thread.
         * If the computing node does not respond, then try again after 5 seconds.
         * If there is no response, then the server thread does the client's work itself.
         */
        @Override
        public void run() {
            if (hasNotPrime.get()) {
                return;
            }
            out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                out.println(list.get(i));
            }
            out.flush();
            if (in.hasNextBoolean()) {
                hasNotPrime.set(in.nextBoolean());
            } else {
                try {
                    Thread.sleep(5000);
                    if (in.hasNextBoolean()) {
                        hasNotPrime.set(in.nextBoolean());
                    } else {
                        if (!hasNotPrime.get()) {
                            hasNotPrime.set(Check.hasNotPrime(this.list));
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
