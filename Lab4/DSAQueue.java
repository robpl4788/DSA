package Lab4;
//Adapted from Lab 3 DSAQueue and DSACircularQueue by Robert Lewis 21494561


public class DSAQueue {

    private LinkedList queue = new LinkedList();

    public void push(Object data) {
        queue.pushFront(data);
    }

    public Object peek() {
        return queue.peekFront();
    }

    public Object pop() {
        return queue.popFront();
    }

}
