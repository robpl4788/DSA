package Lab3;

public class DSAShuffleQueue extends DSAQueue{
    public DSAShuffleQueue(int capacity) {
        super(capacity);
    }

    public Object peek() {
        if (size == 0) {
            throw new QueueException("Queue is empty, but data is being requested.");
        }
        return array[0];
    }

    public Object pop() {
        if (size == 0) {
            throw new QueueException("Queue is empty, but data is being removed.");
        }
        size --;

        Object data = array[end];
        for (int i = end - 1; i >= 0; i --) {
            Object temp = array[i];
            array[i] = data;
            data = temp;
        }

        return data;
    }

}