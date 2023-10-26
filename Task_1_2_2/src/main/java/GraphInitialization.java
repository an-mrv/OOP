import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class for initializing the initial state of a graph from a file.
 * The names of vertices and edges are initialized as strings.
 */
public class GraphInitialization {

    /**
     * Representation of a graph in a file and its further storage in a file in the form of
     * an incidence matrix. The first number in the file is the number of vertices. If an
     * edge goes from one vertex to another vertex, then the name of this vertex is written
     * at the end of the corresponding row in the adjacency matrix.
     *
     * @param filename the name of the file to read
     * @return filled graph
     */
    public GraphAdjacencyMatrix<String> byAdjMatrix(String filename) {
        int vertexCount;
        int edgesCount;
        int weight;
        String from;
        String to;
        String edge;
        String line;
        String vertex;
        String[] splited;
        ArrayList<String> vertices = new ArrayList<>();
        GraphAdjacencyMatrix<String> g = new GraphAdjacencyMatrix<>();
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        vertexCount = fileScanner.nextInt();
        fileScanner.nextLine();

        line = fileScanner.nextLine();
        splited = line.split("\\s+");
        for (int i = 0; i < vertexCount; i++) {
            vertex = splited[i];
            vertices.add(vertex);
            g.addVertex(vertex);
        }
        for (int i = 0; i < vertexCount; i++) {
            edgesCount = 0;
            line = fileScanner.nextLine();
            splited = line.split("\\s+");
            from = splited[0];
            for (int j = 1; j <= vertexCount; j++) {
                weight = Integer.parseInt(splited[j]);
                if (weight != 0) {
                    to = vertices.get(j - 1);
                    edge = splited[vertexCount + 1 + edgesCount];
                    edgesCount++;
                    g.addEdge(from, to, weight, edge);

                }
            }
        }
        return g;
    }

    /**
     * Representation of a graph in a file and its further storage in a file in the form of
     * an adjacency list. The first number in the file is the number of vertices. Then the
     * names of all the vertices. For each vertex in the graph, the number of incident vertices
     * is written. Each row with the name of the incident vertex contains the weight and the
     * name of the edge that connects them.
     *
     * @param filename the name of the file to read
     * @return filled graph
     */
    public GraphAdjacencyList<String> byAdjList(String filename) {
        int vertexCount;
        int edgesCount;
        int weight;
        String from;
        String to;
        String edge;
        String line;
        String[] splited;
        GraphAdjacencyList<String> g = new GraphAdjacencyList<>();
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        vertexCount = fileScanner.nextInt();
        fileScanner.nextLine();

        line = fileScanner.nextLine();
        splited = line.split("\\s+");

        for (int i = 0; i < vertexCount; i++) {
            g.addVertex(splited[i]);
        }

        for (int i = 0; i < vertexCount; i++) {
            line = fileScanner.nextLine();
            splited = line.split("\\s+");
            from = splited[0];
            edgesCount = Integer.parseInt(splited[1]);
            for (int j = 0; j < edgesCount; j++) {
                line = fileScanner.nextLine();
                splited = line.split("\\s+");
                to = splited[0];
                weight = Integer.parseInt(splited[1]);
                edge = splited[2];
                g.addEdge(from, to, weight, edge);
            }
        }
        return g;
    }

    /**
     * Representation of a graph in a file and its further storage in a file in the form of
     * an incidence matrix. The first number in the file is the number of vertices, the second
     * number is the number of edges. In the incidence matrix, each row corresponds to one edge
     * of the graph. The first parameter of the string is the name of the edge, the last
     * is its weight.
     * 1 - if the edge is outgoing to the corresponding vertex,
     * -1 - if the edge is incoming to the corresponding vertex,
     * 0 - if they are not incident.
     *
     * @param filename the name of the file to read
     * @return filled graph
     */
    public GraphIncidenceMatrix<String> byIncMatrix(String filename) {
        int vertexCount;
        int edgesCount;
        int weight;
        String from;
        String to;
        String edge;
        String line;
        String vertex;
        String[] splited;
        ArrayList<String> vertices = new ArrayList<>();
        GraphIncidenceMatrix<String> g = new GraphIncidenceMatrix<>();
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        vertexCount = fileScanner.nextInt();
        edgesCount = fileScanner.nextInt();
        fileScanner.nextLine();

        line = fileScanner.nextLine();
        splited = line.split("\\s+");

        for (int i = 0; i < vertexCount; i++) {
            vertex = splited[i];
            vertices.add(vertex);
            g.addVertex(vertex);
        }

        for (int i = 0; i < edgesCount; i++) {
            from = null;
            to = null;
            line = fileScanner.nextLine();
            splited = line.split("\\s+");
            edge = splited[0];
            for (int j = 1; j <= vertexCount; j++) {
                if (splited[j].equals("1")) {
                    from = vertices.get(j - 1);
                } else if (splited[j].equals("-1")) {
                    to = vertices.get(j - 1);
                }
            }
            weight = Integer.parseInt(splited[splited.length - 1]);
            g.addEdge(from, to, weight, edge);
        }
        return g;
    }
}
