import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the task_1_2_1.
 */

public class Tests {

    @Test
    void testAddChild() {
        Tree<String> tree1 = new Tree <>("R1");
        var a = tree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree1 = new Tree <>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        Tree<String> tree2 = new Tree <>("R1");
        Tree<String> subtree2 = new Tree <>("A");
        subtree2.addChild("B");
        tree2.addChild(subtree2);
        var c = tree2.addChild("R2");
        c.addChild("C");
        c.addChild("D");

        assert(tree1.equals(tree2));

        tree2.addChild("E");
        assert(!tree1.equals(tree2));
    }

    @Test
    void testRemove() {
        Tree<String> tree1 = new Tree <>("R1");
        var a = tree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree1 = new Tree <>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);
        b.remove();

        Tree<String> tree2 = new Tree <>("R1");
        Tree<String> subtree2 = new Tree <>("A");
        var d = subtree2.addChild("B");
        tree2.addChild(subtree2);
        var c = tree2.addChild("R2");
        c.addChild("C");
        c.addChild("D");
        d.remove();

        assert(tree1.equals(tree2));
    }

    @Test
    void testEquals() {
        Tree<Integer> tree1 = new Tree <>(1);
        tree1.addChild(2);
        var a = tree1.addChild(3);
        a.addChild(4);

        Tree<Integer> tree2 = new Tree <>(1);
        tree2.addChild(2);
        Tree<Integer> subtree2 = new Tree <>(3);
        subtree2.addChild(4);
        tree2.addChild(subtree2);

        Tree<Integer> tree3 = new Tree <>(1);
        tree3.addChild(2);
        Tree<Integer> subtree3 = new Tree <>(3);
        subtree3.addChild(5);
        tree3.addChild(subtree3);

        assert(tree1.equals(tree2));
        assert(!tree1.equals(tree3));
        assert(!tree1.equals(null));
        assert(tree1.equals(tree1));
    }

    @Test
    void testBfs() {
        Tree<String> tree1 = new Tree <>("R1");
        var a = tree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree1 = new Tree <>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        ArrayList<String> bfs = new ArrayList<String>();
        Iterator<String> it = tree1.iterator();
        while (it.hasNext()) {
            var value = it.next();
            bfs.add(value);
        }
        List<String> bfsRight = new ArrayList<>(Arrays.asList("R1", "A", "R2", "B", "C", "D"));
        assertEquals(bfs, bfsRight);
    }

    @Test
    void testBfsEmpty() {
        Tree<String> tree = new Tree <>("R1");
        tree.remove();
        Iterator<String> it = tree.iterator();
        while(it.hasNext()) {
            fail();
        }
    }

    @Test
    void testDfs() {
        Tree<String> tree1 = new Tree <>("R1");
        var a = tree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree1 = new Tree <>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        ArrayList<String> dfs = new ArrayList<String>();
        Iterator<String> it = tree1.dfsiterator();
        while (it.hasNext()) {
            var value = it.next();
            dfs.add(value);
        }
        List<String> dfsRight = new ArrayList<>(Arrays.asList("R1", "A", "B", "R2", "C", "D"));
        assertEquals(dfs, dfsRight);
    }

    @Test
    void testDfsEmpty() {
        Tree<String> tree = new Tree <>("R1");
        tree.remove();
        Iterator<String> it = tree.dfsiterator();
        while(it.hasNext()) {
            fail();
        }
    }

    @Test
    void testBfsConcurrentModificationException() {
        Tree<String> tree1 = new Tree <>("R1");
        var a = tree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree1 = new Tree <>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        Iterator<String> it = tree1.iterator();
        ConcurrentModificationException thrown = Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            a.remove();
            while (it.hasNext()) {
                it.next();
            }
        });

        ConcurrentModificationException thrown2 = Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            it.remove();
        });
    }

    @Test
    void testDfsConcurrentModificationException() {
        Tree<String> tree1 = new Tree <>("R1");
        var a = tree1.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree1 = new Tree <>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        Iterator<String> it = tree1.dfsiterator();
        ConcurrentModificationException thrown = Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            a.addChild("H");
            while (it.hasNext()) {
                it.next();
            }
        });

        ConcurrentModificationException thrown2 = Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            it.remove();
        });
    }
}