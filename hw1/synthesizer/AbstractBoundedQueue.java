package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;
    public int capacity()
    public int fillCount()
    public boolean isEmpty()
    public boolean isFull()
    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);

}
