/**
 * Class for working with vertex objects. The vertex name is set by the user.
 * @param <T> type of names of vertices in the graph
 */
public class Vertex<T> {
    private T name;

    /**
     * Constructor.
     * @param value the name of vertex
     */
    Vertex(T value) {
        this.name = value;
    }

    /**
     * @return the name of vertex
     */
    public T getName() {
        return this.name;
    }
}
