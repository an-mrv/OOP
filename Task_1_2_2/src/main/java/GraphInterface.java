import java.util.ArrayList;

/**
 * Interface of general methods for working with graphs.
 *
 * @param <T> type of names of vertices and edges in the graph
 */

public interface GraphInterface<T> {

    /**
     * Return the vertex object by name.
     *
     * @param name name of vertex
     * @return vertex
     */
    public Vertex<T> getVertexByName(T name) throws IllegalArgumentException;

    /**
     * Return the edge object by name.
     *
     * @param name name of edge
     * @return edge
     */
    public Edge<T> getEdgeByName(T name) throws IllegalArgumentException;

    /**
     * Adding a new vertex to the graph.
     *
     * @param name name of new vertex
     */
    public void addVertex(T name) throws IllegalArgumentException;

    /**
     * Adding a new edge to the graph.
     *
     * @param from the vertex for which the edge is outgoing
     * @param to the vertex for which the edge is ingoing
     * @param weight the weight of edge
     * @param name the name of edge
     */
    public void addEdge(T from, T to, int weight, T name) throws IllegalArgumentException;

    /**
     * Removing a vertex and all its incident edges from the graph.
     *
     * @param name the name of vertex
     */
    public void removeVertex(T name) throws IllegalArgumentException;

    /**
     * Removing an edge from the graph.
     *
     * @param name the name of edge
     */
    public void removeEdge(T name) throws IllegalArgumentException;

    /**
     * Dijkstra's algorithm for sorting vertices by distance from a given vertex.
     *
     * @param vertexName the name of given vertex
     * @return sorted by distance from a given vertex array
     */
    public ArrayList<DistanceFromCurrVertex<T>> dijkstraAlgorithm(T vertexName);
}
