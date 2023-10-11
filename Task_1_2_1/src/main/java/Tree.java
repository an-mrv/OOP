import java.util.*;

/**
 * Сlass for working with trees.
 *
 * @param <T> type for vertex values
 */
public class Tree<T> implements Iterable<T> {
    private T data;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;
    private int modCount = 0; //modification count

    public Tree(T data) {
        this.data = data;
        this.children = new ArrayList<Tree<T>>();
        this.parent = null;
    }

    private void changeModCountForAncestors() {
        Tree<T> curParent = this.parent;
        while (curParent != null) {
            curParent.modCount++;
            curParent = curParent.parent;
        }
    }

    /**
     * Add the value of a new child.
     *
     * @param data value for the new vertex
     *
     * @return child's subtree
     */
    public Tree<T> addChild(T data) {
        this.modCount++;
        this.changeModCountForAncestors();
        Tree<T> newChild = new Tree<>(data);
        this.children.add(newChild);
        newChild.parent = this;
        return newChild;
    }

    /**
     * Add the subtree as a child.
     *
     * @param data subtree
     */
    public void addChild(Tree<T> data) {
        this.modCount++;
        this.changeModCountForAncestors();
        this.children.add(data);
        data.parent = this;
    }

    /**
     * Deleting a subtree.
     */
    public void remove() {
        this.modCount++;
        this.changeModCountForAncestors();
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.children.clear();
        this.data = null;
    }

    /**
     * Checking for equality of two trees.
     *
     * @param obj tree
     *
     * @return is equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tree)) {
            return false;
        }
        if (((Tree<?>) obj).data != this.data) {
            return false;
        }
        if (((Tree<?>) obj).children.size() != this.children.size()) {
            return false;
        }
        for (int i = 0; i < this.children.size(); i++) {
            if (!this.children.get(i).equals(((Tree<?>) obj).children.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Iterator for the BFS.
     *
     * @return BFS iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new BfsIterator<>(this);
    }

    /**
     * Class BfsIterator.
     *
     * @param <T> type for vertex values
     */
    class BfsIterator<T> implements Iterator<T> {
        private int expectedModCount;
        private Deque<Tree<T>> deque = new ArrayDeque<>();

        BfsIterator(Tree<T> t) {
            expectedModCount = t.modCount;
            if (t.data != null) {
                deque.addLast(t);
            }
        }

        /**
         * checking that there is the next element.
         *
         * @return has next element or not
         */
        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.deque.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * returns the next element.
         *
         * @return next element
         */
        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> a = this.deque.pop();
            for (int i = 0; i < a.children.size(); i++) {
                if (a.children.get(i).data != null) {
                    this.deque.addLast(a.children.get(i));
                }
            }
            return a.data;
        }

        /**
         * exception when calling remove.
         */
        @Override
        public void remove() throws ConcurrentModificationException {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Iterator for the DFS.
     *
     * @return DFS iterator
     */
    public Iterator<T> dfsiterator() {
        return new DfsIterator<>(this);
    }

    /**
     * Class DfsIterator.
     *
     * @param <T> type for vertex values
     */
    class DfsIterator<T> implements Iterator<T> {
        private int expectedModCount;
        private Deque<Tree<T>> stack = new ArrayDeque<>();
        private Deque<Tree<T>> visited = new ArrayDeque<>();

        DfsIterator(Tree<T> t) {
            expectedModCount = t.modCount;
            if (t.data != null) {
                stack.addLast(t);
            }
        }

        /**
         * checking that there is the next element.
         *
         * @return has next element or not
         */
        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.stack.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * returns the next element.
         *
         * @return next element
         */
        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> a = this.stack.pop();
            for (int i = a.children.size() - 1; i >= 0; i--) {
                if (a.children.get(i).data != null) {
                    this.stack.addFirst(a.children.get(i));
                }
            }
            this.visited.addLast(a);
            return a.data;
        }

        /**
         * exception when calling remove.
         */
        @Override
        public void remove() throws ConcurrentModificationException {
            throw new ConcurrentModificationException();
        }
    }
}
