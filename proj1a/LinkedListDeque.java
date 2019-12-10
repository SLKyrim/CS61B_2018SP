/**
 * LinkedListDeque class has methods which are as follows :
 * public void addFirst(T item): Adds an item of type T to the front of the deque. [no loop or recursion][constant time]
 * public void addLast(T item): Adds an item of type T to the back of the deque. [no loop or recursion][constant time]
 * public boolean isEmpty(): Returns true if deque is empty, false otherwise.
 * public int size(): Returns the number of items in the deque. [constant time]
 * public void printDeque(): Prints the items in the deque from first to last, separated by a space.
 * public T removeFirst(): Removes and returns the item at the front of the deque. If no such item exists, returns null. [no loop or recursion][constant time]
 * public T removeLast(): Removes and returns the item at the back of the deque. If no such item exists, returns null. [no loop or recursion][constant time]
 * public T get(int index): Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque! [Use iteration][constant time] 
 * public LinkedListDeque(): Creates an empty linked list deque.
 * public T getRecursive(int index): Same as get, but uses recursion.
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
        sentinel = new Node(null, sentinel, sentinel); // circular sentinel topology
        size = 0;
    }

    /** Adds x to the front of the deque. */
    public void addFirst(T x) {
        sentinel.next = new Node(x, sentinel, sentinel.next);
        size += 1;
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) return null;
        T tmp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return tmp;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T x) {
        sentinel.prev = new Node(x, sentinel.prev, sentinel);
        // sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        Node curr = sentinel;
        for (int i = 0; i < size; i++) {
            curr = curr.next;
            System.out.println(curr.item);
        }
    }
}