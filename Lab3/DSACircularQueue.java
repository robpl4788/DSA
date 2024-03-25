package Lab3;

public class DSACircularQueue extends DSAQueue {
    int start = 0;
    
    public DSACircularQueue(int Capacity) {
        super(Capacity);
    }

    public Object peek() {
        if (size == 0) {
            throw new QueueException("Queue is empty, but data is being requested.");
        }
        return array[start];
    }

    public Object pop() {
        if (size == 0) {
            throw new QueueException("Queue is empty, but data is being removed.");
        }
        size --;

        Object data = array[start];

        start ++;
        if (start == capacity) {
            start = 0;
        }
        return data;
    }

    public String getString() {
        String out = "";
        for (int i = 0; i < size; i ++) {
            out += array[(start + i) % capacity];
            out += ",";
        }

        return out;
    }

    
}
