import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for Task_1_2_2.
 */

public class Tests {
    @Test
    public void testGraphAdjacencyMatrix() {
        GraphInitialization initialization = new GraphInitialization();
        GraphAdjacencyMatrix<String> g =
                initialization.byAdjMatrix("src/main/java/graphbyAdjMatrix.txt");
        ArrayList<DistanceFromCurrVertex<String>> res;
        res = g.dijkstraAlgorithm("C");
        ArrayList<DistanceFromCurrVertex<String>> answ = new ArrayList<>();
        answ.add(new DistanceFromCurrVertex<>("C", 0));
        answ.add(new DistanceFromCurrVertex<>("D", 2));
        answ.add(new DistanceFromCurrVertex<>("E", 4));
        answ.add(new DistanceFromCurrVertex<>("F", 5));
        answ.add(new DistanceFromCurrVertex<>("G", 9));
        answ.add(new DistanceFromCurrVertex<>("B", 10));
        answ.add(new DistanceFromCurrVertex<>("A", 14));
        for (int i = 0; i < res.size(); i++) {
            if (!answ.get(i).equals(res.get(i))) {
                fail();
            }
        }

        g.addVertex("H"); //an unreachable vertex
        answ.add(new DistanceFromCurrVertex<>("H", 1000000000));
        res = g.dijkstraAlgorithm("C");
        for (int i = 0; i < res.size(); i++) {
            if (!answ.get(i).equals(res.get(i))) {
                fail();
            }
        }

        g.addVertex("I");
        g.addEdge("A", "I", 1, "e21");
        g.addEdge("I", "A", 1, "e22");
        g.addEdge("I", "G", 2, "e23");
        g.addEdge("G", "I", 2, "e24");

        res = g.dijkstraAlgorithm("C");
        answ.remove(6);
        answ.add(6, new DistanceFromCurrVertex<>("I", 11));
        answ.add(7, new DistanceFromCurrVertex<>("A", 12));
        for (int i = 0; i < res.size(); i++) {
            if (!answ.get(i).equals(res.get(i))) {
                fail();
            }
        }

        Vertex<String> v = g.getVertexByName("A");
        assert (v.getName().equals("A"));

        Edge<String> e = g.getEdgeByName("e1");
        assert (e.getName().equals("e1"));

        IllegalArgumentException thrown =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.addVertex("A");
                });

        g.removeVertex("A");
        IllegalArgumentException thrown1 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getVertexByName("A");
                });


        IllegalArgumentException thrown2 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getEdgeByName("e1");
                });

        g.removeEdge("e16");
        IllegalArgumentException thrown3 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getEdgeByName("e16");
                });

        IllegalArgumentException thrown4 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getVertexByName("Z");
                });

        IllegalArgumentException thrown5 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.addEdge("I", "A", 6, "e25");
                });
    }

    @Test
    public void testGraphAdjacencyList() {
        GraphInitialization initialization = new GraphInitialization();
        GraphAdjacencyList<String> g =
                initialization.byAdjList("src/main/java/graphbyAdjList.txt");
        ArrayList<DistanceFromCurrVertex<String>> res;
        res = g.dijkstraAlgorithm("C");
        ArrayList<DistanceFromCurrVertex<String>> answ = new ArrayList<>();
        answ.add(new DistanceFromCurrVertex<>("C", 0));
        answ.add(new DistanceFromCurrVertex<>("D", 2));
        answ.add(new DistanceFromCurrVertex<>("E", 4));
        answ.add(new DistanceFromCurrVertex<>("F", 5));
        answ.add(new DistanceFromCurrVertex<>("G", 9));
        answ.add(new DistanceFromCurrVertex<>("B", 10));
        answ.add(new DistanceFromCurrVertex<>("A", 14));
        for (int i = 0; i < res.size(); i++) {
            if (!answ.get(i).equals(res.get(i))) {
                fail();
            }
        }

        g.removeEdge("e1");
        g.removeEdge("e4");
        g.removeEdge("e9");
        g.removeEdge("e15");
        g.removeEdge("e16");
        g.removeEdge("e20");
        answ.remove(3);
        answ.add(new DistanceFromCurrVertex<>("F", 1000000000));
        res = g.dijkstraAlgorithm("C");
        for (int i = 0; i < res.size(); i++) {
            if (!answ.get(i).equals(res.get(i))) {
                fail();
            }
        }

        g.addVertex("I");
        g.addEdge("A", "I", 1, "e1");

        Vertex<String> v = g.getVertexByName("A");
        assert (v.getName().equals("A"));

        Edge<String> e = g.getEdgeByName("e1");
        assert (e.getName().equals("e1"));

        IllegalArgumentException thrown =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.addVertex("A");
                });

        g.removeVertex("A");
        IllegalArgumentException thrown1 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getVertexByName("A");
                });

        IllegalArgumentException thrown2 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getEdgeByName("e1");
                });

        IllegalArgumentException thrown3 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.removeEdge("e16");
                });

        IllegalArgumentException thrown4 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getVertexByName("Z");
                });
    }

    @Test
    public void testGraphIncidenceMatrix() {
        GraphInitialization initialization = new GraphInitialization();
        GraphIncidenceMatrix<String> g =
                initialization.byIncMatrix("src/main/java/graphbyIncMatrix.txt");
        ArrayList<DistanceFromCurrVertex<String>> res;
        res = g.dijkstraAlgorithm("C");
        ArrayList<DistanceFromCurrVertex<String>> answ = new ArrayList<>();
        answ.add(new DistanceFromCurrVertex<>("C", 0));
        answ.add(new DistanceFromCurrVertex<>("D", 2));
        answ.add(new DistanceFromCurrVertex<>("E", 4));
        answ.add(new DistanceFromCurrVertex<>("F", 5));
        answ.add(new DistanceFromCurrVertex<>("G", 9));
        answ.add(new DistanceFromCurrVertex<>("B", 10));
        answ.add(new DistanceFromCurrVertex<>("A", 14));
        for (int i = 0; i < res.size(); i++) {
            if (!answ.get(i).equals(res.get(i))) {
                fail();
            }
        }

        ArrayList<DistanceFromCurrVertex<String>> answ1 = new ArrayList<>();
        answ1.add(new DistanceFromCurrVertex<>("G", 0));
        answ1.add(new DistanceFromCurrVertex<>("E", 5));
        answ1.add(new DistanceFromCurrVertex<>("C", 9));
        answ1.add(new DistanceFromCurrVertex<>("F", 10));
        answ1.add(new DistanceFromCurrVertex<>("D", 11));
        answ1.add(new DistanceFromCurrVertex<>("B", 19));
        answ1.add(new DistanceFromCurrVertex<>("A", 23));
        res = g.dijkstraAlgorithm("G");
        for (int i = 0; i < res.size(); i++) {
            if (!answ1.get(i).equals(res.get(i))) {
                fail();
            }
        }

        ArrayList<DistanceFromCurrVertex<String>> answ2 = new ArrayList<>();
        g.addVertex("I");
        answ2.add(new DistanceFromCurrVertex<>("I", 0));
        answ2.add(new DistanceFromCurrVertex<>("A", 1000000000));
        answ2.add(new DistanceFromCurrVertex<>("B", 1000000000));
        answ2.add(new DistanceFromCurrVertex<>("C", 1000000000));
        answ2.add(new DistanceFromCurrVertex<>("D", 1000000000));
        answ2.add(new DistanceFromCurrVertex<>("E", 1000000000));
        answ2.add(new DistanceFromCurrVertex<>("F", 1000000000));
        answ2.add(new DistanceFromCurrVertex<>("G", 1000000000));

        res = g.dijkstraAlgorithm("I");
        for (int i = 0; i < res.size(); i++) {
            if (!answ2.get(i).equals(res.get(i))) {
                fail();
            }
        }

        Vertex<String> v = g.getVertexByName("A");
        assert (v.getName().equals("A"));

        Edge<String> e = g.getEdgeByName("e1");
        assert (e.getName().equals("e1"));

        IllegalArgumentException thrown =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.addVertex("A");
                });

        g.removeVertex("A");
        IllegalArgumentException thrown1 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getVertexByName("A");
                });

        IllegalArgumentException thrown2 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getEdgeByName("e1");
                });

        g.removeEdge("e16");
        IllegalArgumentException thrown3 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getEdgeByName("e16");
                });

        IllegalArgumentException thrown4 =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    g.getVertexByName("Z");
                });
    }
}
