/**
 * SLList from lecture 4->5
 */
package lectrue;

public class SLList<T> {

    private class TNode {
        private T item;
        private TNode next;

        public TNode(T i, TNode n) {
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private TNode sentinel;
    private int size;

    /** Creates an empty SLList. */
    public SLList() {
        sentinel = new TNode(null, null);
        size = 0;
    }

    /** Creates a SLList with a node */
    public SLList(T x) {
        sentinel = new TNode(null, null);
        sentinel.next = new TNode(x, null);
        size = 1;
    }

    /** Adds x to the front of the list. */
    public void addFirst(T x) {
        sentinel.next = new TNode(x, sentinel.next);
        size += 1;
    }

    /** Returns the first item in the list. */
    public T getFirst() {
        return sentinel.next.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(T x) {
        size += 1;

        TNode p = sentinel;

        /* Move p until it reaches the end of the list. */
        while (p.next != null) {
            p = p.next;
        }

        p.next = new TNode(x, null);
    }

    public int size() {
        return size;
    }
}
