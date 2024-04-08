package Lab4;

//Adapted from Lab 3 DSAStack by Robert Lewis 21494561

public class DSAStack {
    private LinkedList stack = new LinkedList();

    public void push(Object data) {
        stack.pushBack(data);
    }

    public Object pop() {
        return stack.popBack();
    }

    public Object peek() {
        return stack.peekBack();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
