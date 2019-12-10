/**
 * ArrayDeque class has methods which are as follows :
 * public void addFirst(T item): Adds an item of type T to the front of the deque. [no loop or recursion][constant time]
 * public void addLast(T item): Adds an item of type T to the back of the deque. [no loop or recursion][constant time][Done]
 * public boolean isEmpty(): Returns true if deque is empty, false otherwise.[Done]
 * public int size(): Returns the number of items in the deque. [constant time][Done]
 * public void printDeque(): Prints the items in the deque from first to last, separated by a space.[Done]
 * public T removeFirst(): Removes and returns the item at the front of the deque. If no such item exists, returns null. [no loop or recursion][constant time]
 * public T removeLast(): Removes and returns the item at the back of the deque. If no such item exists, returns null. [no loop or recursion][constant time][Done]
 * public T get(int index): Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque! [Use iteration][constant time][Done]
 * public ArrayDeque(): Creates an empty array deque. [Done]
 */
public class ArrayDeque<Item> {
    
    private Item[] items;
    private int size;
    
    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (Item []) new Object[8];
        size = 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Gets the item at the given index */
    public Item get(int index) {
        if (index > size - 1) return null;
        return items[index];
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Adds an item of type Item to the back of the deque. */
    public void addLast(Item item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = item;
        size = size + 1;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        Item[] a = (Item []) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public Item removeLast() {
        Item x = get(size - 1);
        if (x != null) {
            items[size - 1] = null; // nulling out deleted items to save memory
            size = size - 1;
        } 
        return x;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[i]);
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /** Adds an item of type Item to the front of the deque. */
    public void addFirst(Item item) {
        if (size == items.length) {
            resize(size * 2);
        }
        System.arraycopy(items, 0, items, 1, size);
        items[0] = item;
        size = size + 1;
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public Item removeFirst() {
        Item x = get(0);
        if (x != null) {
            System.arraycopy(items, 1, items, 0, size - 1);
            items[size - 1] = null;
            size = size - 1;
        } 
        return x;
    }
}