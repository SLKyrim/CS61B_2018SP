/**
 * LinkedListDeque class has methods which are as follows :
 * public void addFirst(T item): Adds an item of type T to the front of the deque. [no loop or recursion][constant time][Done]
 * public void addLast(T item): Adds an item of type T to the back of the deque. [no loop or recursion][constant time][Done]
 * public boolean isEmpty(): Returns true if deque is empty, false otherwise.[Done]
 * public int size(): Returns the number of items in the deque. [constant time][Done]
 * public void printDeque(): Prints the items in the deque from first to last, separated by a space.[Done]
 * public T removeFirst(): Removes and returns the item at the front of the deque. If no such item exists, returns null. [no loop or recursion][constant time][Done]
 * public T removeLast(): Removes and returns the item at the back of the deque. If no such item exists, returns null. [no loop or recursion][constant time][Done]
 * public T get(int index): Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque! [Use iteration][constant time][Done]
 * public LinkedListDeque(): Creates an empty linked list deque.[Done]
 * public T getRecursive(int index): Same as get, but uses recursion.[Done]
 */
public class LinkedListDeque<T> {

    /** Nested class for node of the list. */
    public class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node p, Node n){
            item = i;
            prev = p;
            next = n;
        }
    }

    Node sentinel; // sentinel node to ensure the invariant of the list
    int size; // caching the size of the list

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Creates an empty linked list deque */
    public LinkedListDeque() {
        /* Don't use sentinel = new node(null, sentinel, sentinel), 
           otherwise will cause Exception in thread "main" java.lang.NullPointerException,
           don't know why yet. */
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds x to the front of the deque. */
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) return null;
        T tmp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return tmp;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (size == 0) return null;
        T tmp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return tmp;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
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

    /** Use iteration to get the item at the given index, where 0 is the front, 1 is the next item, and so forth. 
     * If no such item exists, returns null. */
    public T get(int index) {
        if (index > size - 1) return null;
        Node curr = sentinel;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if (index > size - 1) return null;
        return getRecursive(index + 1, sentinel);
    }
    
    /* middleman of the getRecursive */
    private T getRecursive(int index, Node curr) {
        if (index == 0) {
            return curr.item;
        }
        return getRecursive(index - 1, curr.next);
    }
}