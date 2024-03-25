package Lab3;

public class DSAQueue {
    Object[] array;
    int end = -1;
    int size = 0;
    int capacity;

    public DSAQueue(int Capacity) {
        capacity = Capacity;
        
        array = new Object[capacity];
    }

    public void push(Object data) {

        if (size == capacity) {
            throw new QueueException("Queue is at capacity of " + String.valueOf(capacity) + ", but data is being added.");
        }

        size ++;
        end ++;
        array[end] = data;
    }
    
    public int getCurrentSize() {
        return size;
    }

}
