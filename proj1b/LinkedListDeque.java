/**
 * LinkedListDeque class
 */
public class LinkedListDeque<Item> implements Deque<Item> {

    /** Nested class for node of the list. */
    private class Node {
        private Item item;
        private Node prev;
        private Node next;

        public Node(Item i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node sentinel; // sentinel node to ensure the invariant of the list
    private int size; // caching the size of the list

    /** Returns true if deque is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Creates an empty linked list deque */
    public LinkedListDeque() {
        /* Don't use sentinel = new node(null, sentinel, sentinel), 
           otherwise will cause 
           Exception in thread "main" java.lang.NullPointerException,
           don't know why yet. */
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds x to the front of the deque. */
    @Override
    public void addFirst(Item item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(Item item) {
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /** Removes and returns the item at the front of the deque. 
     * If no such item exists, returns null. */
    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        Item tmp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return tmp;
    }

    /** Removes and returns the item at the back of the deque. 
     * If no such item exists, returns null. */
    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        Item tmp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return tmp;
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        Node curr = sentinel;
        for (int i = 0; i < size; i++) {
            curr = curr.next;
            System.out.print(curr.item);
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /** Use iteration to get the item at the given index,
     * where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. */
    @Override
    public Item get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }
        Node curr = sentinel;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    /** Same as get, but uses recursion. */
    public Item getRecursive(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }
        return getRecursive(index + 1, sentinel);
    }
    
    /* Middleman of the getRecursive */
    private Item getRecursive(int index, Node curr) {
        if (index == 0) {
            return curr.item;
        }
        return getRecursive(index - 1, curr.next);
    }
}
