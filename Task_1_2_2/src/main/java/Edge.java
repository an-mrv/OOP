/**
 * Class for working with edge objects. The edge name is set by the user.
 *
 * @param <T> type of names of edges in the graph
 */
public class Edge<T> {
    private Vertex<T> from;
    private Vertex<T> to;
    private int weight;
    private T name;

    /**
     * Constructor.
     *
     * @param from the vertex for which the edge is outgoing
     * @param to the vertex for which the edge is ingoing
     * @param weight the weight of edge
     * @param name the name of edge
     */
    Edge(Vertex<T> from, Vertex<T> to, int weight, T name) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.name = name;
    }

    /**
     * Get the weight of edge.
     *
     * @return the weight of edge
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Get the vertex for which the edge is outgoing.
     *
     * @return the vertex for which the edge is outgoing
     */
    public Vertex<T> getFrom() {
        return this.from;
    }

    /**
     * Get the vertex for which the edge is ingoing.
     *
     * @return the vertex for which the edge is ingoing
     */
    public Vertex<T> getTo() {
        return this.to;
    }

    /**
     * Get the name of edge.
     *
     * @return the name of edge
     */
    public T getName() {
        return this.name;
    }
}
