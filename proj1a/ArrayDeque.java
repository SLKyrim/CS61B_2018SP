/**
 * ArrayDeque class
 */
public class ArrayDeque<T> {
    
    private T[] items;
    private int size;
    
    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Gets the item at the given index */
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            return null;
        }
        return items[index];
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Adds an item of type Item to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            upsize();
        }
        items[size] = item;
        size = size + 1;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] a = (T []) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Upsize the array when it is full. */
    private void upsize() {
        resize(size * 2);
    }

    /** Judge whether the usage factor of the underlying array is bad */
    private boolean isSparse() {
        // size / items.length < 25% -> 4 * size < item.length
        return items.length >= 16 && 4 * size < items.length;
    }

    /** Upsize the array when it is full. */
    private void downsize() {
        resize(items.length / 2);
    }

    /** Removes and returns the item at the back of the deque. 
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isSparse()) {
            downsize();
        }
        T x = get(size - 1);
        if (x != null) {
            items[size - 1] = null; // nulling out deleted items to save memory
            size = size - 1;
        } 
        return x;
    }

    /** Prints the items in the deque from first to last, 
     * separated by a space. */
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
    public void addFirst(T item) {
        if (size == items.length) {
            upsize();
        }
        System.arraycopy(items, 0, items, 1, size);
        items[0] = item;
        size = size + 1;
    }

    /** Removes and returns the item at the front of the deque. 
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (isSparse()) {
            downsize();
        }
        T x = get(0);
        if (x != null) {
            System.arraycopy(items, 1, items, 0, size - 1);
            items[size - 1] = null;
            size = size - 1;
        } 
        return x;
    }
}
