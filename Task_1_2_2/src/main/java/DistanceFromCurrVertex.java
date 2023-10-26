/**
 * A class to return the result of Dijkstra's algorithm.
 * @param <T> type of names of vertices and edges in the graph
 */
public class DistanceFromCurrVertex<T> {
    private T vertexName;
    private int distance;

    /**
     * Constructor.
     * @param vertexName the name of vertex
     * @param distance the distance from the specified vertex to the current one
     */
    public DistanceFromCurrVertex(T vertexName, int distance) {
        this.vertexName = vertexName;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DistanceFromCurrVertex)) {
            return false;
        }
        if (!((DistanceFromCurrVertex<?>) obj).vertexName.equals(this.vertexName)) {
            return false;
        }
        if (((DistanceFromCurrVertex<?>) obj).distance != this.distance) {
            return false;
        }
        return true;
    }
}
