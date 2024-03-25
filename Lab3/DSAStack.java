package Lab3;

public class DSAStack {
    
    Object[] array;

    int capacity;
    int size;

    public DSAStack(int Capacity) {
        capacity = Capacity;
        size = -1;
        array = new Object[capacity];
    }

    public void push(Object data) {
        
        if (size + 1 == capacity) {
            throw new StackException("Stack is full at size of " + String.valueOf(capacity) + ", but more data is being added.");
        }

        size ++;
        array[size] = data;
    }

    public Object pop() {
        if (size == -1) {
            throw new StackException("Stack is empty, but data is being removed.");
        }


        Object data = array[size];
        size --;
        return data;
    }

    public Object peek() {
        if (size == -1) {
            throw new StackException("Stack is empty, but data is being requested.");
        }

        return array[size];
    }

    public boolean isEmpty() {
        return size < 0;
    }

    public String getString() {
        String out = "";
        for (int i = 0; i <= size; i ++) {
            out += array[i];
            out += ",";
        }

        return out;
    }

    public int getSize() {
        return size + 1;
    }
}
