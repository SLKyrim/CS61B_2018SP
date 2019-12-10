/**
 * ArrayDeque class
 */
public class ArrayDeque<T> {
    
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    
    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Index adds one circularly */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /** Index minus one circularly */
    private int minusOne(int index) {
        // To avoid negetive index, plus items.length is necessary
        return (index - 1 + items.length) % items.length;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Gets the item at the given index */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        int start = plusOne(nextFirst);
        return items[(start + index) % items.length];
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] newDeque = (T []) new Object[capacity];
        /* Acquires the first item index of the old deque */
        int oldIndex = plusOne(nextFirst); 
        for (int newIndex = 0; newIndex < size; newIndex++) {
            newDeque[newIndex] = items[oldIndex];
            oldIndex = plusOne(oldIndex);
        }
        /* New deque starts from true index 0,
        so addFirst(item) will add item to the last index of underlying array,
        namely the next first index will be "capacity - 1". */
        nextFirst = capacity - 1;
        /* Easy to tell that the next last item will at index size */
        nextLast = size;
        items = newDeque;
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

    /** Adds an item of type Item to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            upsize();
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /** Removes and returns the item at the back of the deque. 
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isSparse()) {
            downsize();
        }
        nextLast = minusOne(nextLast);
        T toRemove = items[nextLast];
        items[nextLast] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        return toRemove;
    }

    /** Adds an item of type Item to the front of the deque. */
    public void addFirst(T item) {
        if (size == items.length) {
            upsize();
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Removes and returns the item at the front of the deque. 
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (isSparse()) {
            downsize();
        }
        nextFirst = plusOne(nextFirst);
        T toRemove = items[nextFirst];
        items[nextFirst] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        return toRemove;
    }

    /** Prints the items in the deque from first to last, 
     * separated by a space. */
    public void printDeque() {
        for (int i = plusOne(nextFirst); i < size; i = plusOne(i)) {
            System.out.print(items[i]);
            if (i < size - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
