import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Server class.
 */
public class Server {
    public AtomicBoolean hasNotPrime = new AtomicBoolean(false);
    private List<Integer> list;
    private List<ServerThread> serverList;
    private AtomicInteger numberOfActiveClients;

    /**
     * Constructor.
     *
     * @param list list for checking
     */
    public Server(List<Integer> list) {
        this.list = list;
        this.serverList = new ArrayList<>();
        this.numberOfActiveClients = new AtomicInteger(0);
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
            server.setSoTimeout(10000);
            acceptClients(server);
            if (numberOfActiveClients.get() == 0) {
                hasNotPrime.set(Check.hasNotPrime(this.list));
            } else {
                startServerThreads();
                joinServerThreads();
                closeSocketsAndStreams();
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
        Socket socket = null;
        while (numberOfActiveClients.get() <= list.size()) {
            try {
                socket = server.accept();
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                try {
                    serverList.add(new ServerThread(socket, in, out));
                    numberOfActiveClients.incrementAndGet();
                } catch (IOException e) {
                    socket.close();
                }
            } catch (SocketTimeoutException timeoutException) {
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Split the list of numbers by all clients and run the server threads.
     */
    private void startServerThreads() {
        int clients = numberOfActiveClients.get();
        int lenOfPart = list.size() / clients;
        for (int i = 0; i < clients - 1; i++) {
            serverList.get(i).setList(list.subList(i * lenOfPart, (i + 1) * lenOfPart));
            serverList.get(i).start();
        }
        serverList.get(clients - 1).setList(list.subList((clients - 1) * lenOfPart, list.size()));
        serverList.get(clients - 1).start();
    }

    /**
     * Waiting for the completion of all server threads.
     */
    private void joinServerThreads() {
        for (int i = 0; i < serverList.size(); i++) {
            try {
                serverList.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Closing sockets and in/out streams for each server thread from server list and
     * sending -1 to the client so that it ends.
     */
    private void closeSocketsAndStreams() {
        for (int i = 0; i < serverList.size(); i++) {
            ServerThread currentTread = serverList.get(i);
            if (!currentTread.unavailable.get()) {
                Socket socket = currentTread.getSocket();
                PrintWriter out = currentTread.getOut();
                Scanner in = currentTread.getIn();
                out.println(-1);
                out.flush();
                out.close();
                in.close();
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Thread for working with the client.
     */
    public class ServerThread extends Thread {
        public AtomicBoolean unavailable = new AtomicBoolean(false);
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        private List<Integer> list;

        /**
         * Constructor.
         *
         * @param socket socket for interaction with the client
         * @param in input stream
         * @param out output stream
         */
        public ServerThread(Socket socket, Scanner in, PrintWriter out) throws IOException {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }

        /**
         * Set the list.
         *
         * @param list the part of the source list that the client needs to process
         */
        public void setList(List<Integer> list) {
            this.list = list;
        }

        /**
         * Get the socket for communication with the client.
         *
         * @return socket
         */
        public Socket getSocket() {
            return socket;
        }

        /**
         * Get the input stream for communication with the client.
         *
         * @return input stream
         */
        public Scanner getIn() {
            return in;
        }

        /**
         * Get the output stream for communication with the client.
         *
         * @return output stream
         */
        public PrintWriter getOut() {
            return out;
        }

        /**
         * Run the thread.
         * If the computing node does not respond, then try again after 5 seconds.
         * If there is no response, then looking for an active client in the server list.
         * if the number of active clients is 0, then the server thread does the client's work
         * itself.
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
                            numberOfActiveClients.decrementAndGet();
                            in.close();
                            out.close();
                            try {
                                socket.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            unavailable.set(true);
                            if (numberOfActiveClients.get() > 0) {
                                findFreeClient();
                            } else {
                                hasNotPrime.set(Check.hasNotPrime(this.list));
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /**
         * Search for a free active client in the server list.
         */
        private void findFreeClient() {
            int i = 0;
            while (true) {
                if (numberOfActiveClients.get() == 0) {
                    hasNotPrime.set(Check.hasNotPrime(this.list));
                    break;
                }
                if (i == serverList.size()) {
                    i = 0;
                }
                ServerThread currentThread = serverList.get(i);
                if (!currentThread.isAlive() && !currentThread.unavailable.get()) {
                    currentThread.unavailable.compareAndSet(false, true);
                    try {
                        ServerThread newThread = new ServerThread(currentThread.getSocket(),
                                currentThread.getIn(), currentThread.getOut());
                        newThread.setList(list);
                        newThread.start();
                        try {
                            newThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        currentThread.unavailable.set(false);
                        break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                i++;
            }
        }
    }
}
