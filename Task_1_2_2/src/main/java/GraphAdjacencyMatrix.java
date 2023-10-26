import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing a graph as an adjacency matrix.
 * The structure of the adjacency matrix: HashMap<NameOfVertex, HashMap<nameOfAdjacentVertex, WeightOfEdge>>.
 * WeightOfEdge = 0, if two vertices are not adjacent.
 * @param <T> type of names of vertices and edges in the graph
 */

public class GraphAdjacencyMatrix<T> {
    private HashMap<T, HashMap<T, Integer>> adjMatrix;
    private HashMap<T, Vertex<T>> vertices;
    private HashMap<T, Edge<T>> edges;
    private HashMap<T, Boolean> visitedVertices;
    private HashMap<T, Integer> distances;

    /**
     * Constructor.
     */
    public GraphAdjacencyMatrix() {
        this.adjMatrix = new HashMap<>();
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    /**
     * Return the vertex object by name.
     * @param name name of vertex
     * @return vertex
     */
    public Vertex<T> getVertexByName(T name) throws IllegalArgumentException {
        Vertex<T> res = vertices.get(name);
        if (res == null) {
            throw new IllegalArgumentException("Vertex with this name does not exist");
        }
        return res;
    }

    /**
     * Return the edge object by name.
     * @param name name of edge
     * @return edge
     */
    public Edge<T> getEdgeByName(T name) throws IllegalArgumentException {
        Edge<T> res = edges.get(name);
        if (res == null) {
            throw new IllegalArgumentException("Edge with this name does not exist");
        }
        return res;
    }

    /**
     * Adding a new vertex to the graph.
     * @param name name of new vertex
     */
    public void addVertex(T name) throws IllegalArgumentException {
        if (vertices.get(name) != null) {
            throw new IllegalArgumentException("This vertex already exists");
        }
        else {
            Vertex<T> v = new Vertex<>(name);
            vertices.put(name, v);
            HashMap<T, Integer> tmp = new HashMap<>();
            for (Map.Entry<T, Vertex<T>> entry : this.vertices.entrySet()) {
                tmp.put(entry.getKey(), 0);
            }
            this.adjMatrix.put(name, tmp);
        }
    }

    /**
     * Adding a new edge to the graph.
     * @param from the vertex for which the edge is outgoing
     * @param to the vertex for which the edge is ingoing
     * @param weight the weight of edge
     * @param name the name of edge
     */
    public void addEdge(T from, T to, int weight, T name) throws IllegalArgumentException {
        if (edges.get(name) != null) {
            throw new IllegalArgumentException("This edge already exists");
        }
        else {
            Vertex<T> vertexFrom = vertices.get(from);
            if (vertexFrom == null) {
                throw new IllegalArgumentException("Vertex 'from' does not exist");
            }
            Vertex<T> vertexTo = vertices.get(to);
            if (vertexTo == null) {
                throw new IllegalArgumentException("Vertex 'to' does not exist");
            }
            Edge<T> e = new Edge<>(vertexFrom, vertexTo, weight, name);
            edges.put(name, e);
            HashMap<T, Integer> tmp = this.adjMatrix.get(from);
            tmp.put(to, weight);
            this.adjMatrix.put(from, tmp);
        }
    }

    /**
     * Removing a vertex and all its incident edges from the graph.
     * @param name the name of vertex
     */
    public void removeVertex(T name) throws IllegalArgumentException {
        Vertex<T> v = this.vertices.get(name);
        ArrayList<T> edgesToRemove = new ArrayList<>();
        if (v != null) {
            this.adjMatrix.remove(name);
            for (Map.Entry<T, Edge<T>> entry : this.edges.entrySet()) {
                Edge<T> e = entry.getValue();
                if (e.getFrom().getName().equals(name)) {
                    edgesToRemove.add(entry.getKey());
                }
            }
            for (Map.Entry<T, HashMap<T, Integer>> entry : this.adjMatrix.entrySet()) {
                HashMap<T, Integer> tmp = entry.getValue();
                tmp.remove(name);
            }
            for (Map.Entry<T, Edge<T>> entry : this.edges.entrySet()) {
                Edge<T> e = entry.getValue();
                if (e.getTo().getName().equals(name)) {
                    edgesToRemove.add(entry.getKey());
                }
            }
            this.vertices.remove(name);
            for (int i = 0; i < edgesToRemove.size(); i++) {
                edges.remove(edgesToRemove.get(i));
            }
        }
        else {
            throw new IllegalArgumentException("A vertex with this name does not exist");
        }
    }

    /**
     * Removing an edge from the graph.
     * @param name the name of edge
     */
    public void removeEdge(T name) throws IllegalArgumentException {
        Edge<T> e = this.edges.get(name);
        if (e != null) {
            Vertex<T> from = e.getFrom();
            Vertex<T> to = e.getTo();
            HashMap<T, Integer> tmp = this.adjMatrix.get(from.getName());
            tmp.put(to.getName(), 0);
            this.edges.remove(name);
        }
        else {
            throw new IllegalArgumentException("An edge with this vertices and weight does not exist");
        }
    }

    /**
     * Dijkstra's algorithm for sorting vertices by distance from a given vertex.
     * @param vertexName the name of given vertex
     * @return sorted by distance from a given vertex array
     */
    public ArrayList<DistanceFromCurrVertex<T>> DijkstraAlgorithm(T vertexName) {
        this.distances = new HashMap<>();
        this.visitedVertices = new HashMap<>();
        ArrayList<DistanceFromCurrVertex<T>> res = new ArrayList<>();
        for (Map.Entry<T, Vertex<T>> entry : this.vertices.entrySet()) {
            T v = entry.getKey();
            this.visitedVertices.put(v, false);
            this.distances.put(v, 1000000000);
        }
        this.distances.put(vertexName, 0);
        int minDist;
        T vertWithMinDist = vertexName;
        while (vertWithMinDist != null) {
            vertWithMinDist = null;
            minDist = 1000000000;
            for (Map.Entry<T, Vertex<T>> entry : this.vertices.entrySet()) {
                T currentVert = entry.getKey();
                if ((!this.visitedVertices.get(currentVert)) && (this.distances.get(currentVert) < minDist)) {
                    minDist = this.distances.get(currentVert);
                    vertWithMinDist = currentVert;
                }
            }
            if (vertWithMinDist != null) {
                HashMap<T, Integer> tmp = this.adjMatrix.get(vertWithMinDist);
                for (Map.Entry<T, Integer> entry : tmp.entrySet()) {
                    T vertTo = entry.getKey();
                    int weight = entry.getValue();
                    if ((this.distances.get(vertTo) > minDist + weight) && (weight != 0)) {
                        this.distances.put(vertTo, minDist + weight);
                    }
                }

                this.visitedVertices.put(vertWithMinDist, true);
                res.add(new DistanceFromCurrVertex<>(vertWithMinDist, this.distances.get(vertWithMinDist)));
            }
        }
        if (res.size() != this.vertices.size()) {
            for (Map.Entry<T, Vertex<T>> entry : this.vertices.entrySet()) {
                T vert = entry.getKey();
                if (!this.visitedVertices.get(vert)) { //an unreachable vertex
                    res.add(new DistanceFromCurrVertex<>(vert, 1000000000));
                    if (res.size() == this.vertices.size()) {
                        break;
                    }
                }
            }
        }
        return res;
    }
}
