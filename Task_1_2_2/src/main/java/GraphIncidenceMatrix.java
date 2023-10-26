import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing a graph as an incidence matrix.
 * The structure of the incidence matrix: HashMap<NameOfEdge, HashMap<nameOfIncidentVertex,
 * connectionOfEdgeAndVertex>>, where connectionOfEdgeAndVertex:
 * 1 - if the edge is outgoing to the incident vertex,
 * -1 - if the edge is incoming to the incident vertex,
 * 0 - if they are not incident.
 * @param <T> type of names of vertices and edges in the graph
 */
public class GraphIncidenceMatrix<T> {
    private HashMap<T, HashMap<T, Integer>> incMatrix;
    private HashMap<T, Vertex<T>> vertices;
    private HashMap<T, Edge<T>> edges;
    private HashMap<T, Boolean> visitedVertices;
    private HashMap<T, Integer> distances;

    /**
     * Constructor.
     */
    public GraphIncidenceMatrix() {
        this.incMatrix = new HashMap<>();
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
            if (!this.incMatrix.isEmpty()) {
                for (Map.Entry<T, HashMap<T, Integer>> entry : this.incMatrix.entrySet()) {
                    HashMap<T, Integer> tmp = entry.getValue();
                    tmp.put(name, 0);
                }
            }
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
            HashMap<T, Integer> tmp = new HashMap<>();
            for (Map.Entry<T, Vertex<T>> entry : this.vertices.entrySet()) {
                T currVertex = entry.getKey();
                if (currVertex.equals(from)) {
                    tmp.put(currVertex, 1);
                }
                else if (currVertex.equals(to)) {
                    tmp.put(currVertex, -1);
                }
                else {
                    tmp.put(currVertex, 0);
                }
            }
            this.incMatrix.put(name, tmp);
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
            for (Map.Entry<T, HashMap<T, Integer>> entry : this.incMatrix.entrySet()) {
                HashMap<T, Integer> tmp = entry.getValue();
                if ((tmp.get(name) == 1) || (tmp.get(name)) == -1) {
                    edgesToRemove.add(entry.getKey());
                }
            }
            for (int i = 0; i < edgesToRemove.size(); i++) {
                this.incMatrix.remove(edgesToRemove.get(i));
                this.edges.remove(edgesToRemove.get(i));
            }
            this.vertices.remove(name);
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
            this.incMatrix.remove(name);
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
                T edgeName, vertTo;
                HashMap<T, Integer> tmp;
                int weight;
                for (Map.Entry<T, HashMap<T, Integer>> entry : this.incMatrix.entrySet()) {
                    tmp = entry.getValue();
                    edgeName = entry.getKey();
                    weight = this.edges.get(edgeName).getWeight();
                    if (tmp.get(vertWithMinDist) == 1) {
                        for (Map.Entry<T, Integer> entry1 : tmp.entrySet()) {
                            if (entry1.getValue() == -1) {
                                vertTo = entry1.getKey();
                                if (this.distances.get(vertTo) > minDist + weight) {
                                    this.distances.put(vertTo, minDist + weight);
                                }
                                break;
                            }
                        }
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
