package Lab4;
public class LinkedList {
    private ListNode head = null;
    private ListNode tail = null;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void pushFront(Object data) {
        ListNode newHead = new ListNode(data, head, null);
        if (head != null) {
            head.setPrev(newHead);
        }
        head = newHead;
        if (tail == null) {
            tail = head;
        }

        size ++;
    }

    public void pushBack(Object data) {
        ListNode newNode = new ListNode(data, null, tail);
        if (tail != null) {
            tail.setNext(newNode);
        }
        tail = newNode;
        if (head == null) {
            head = tail;
        }

        size ++;
    }

    public Object peekFront() {
        return head.getData();
    }

    public Object peekBack() {
        return tail.getData();
    }

    public Object peekIndex(int index) {
        if (index < 0) {
            throw new LinkedListException("Peeked at index out of range, must be greater than or equal to 0, is: " + String.valueOf(index));
        } else if (index > size) {
            throw new LinkedListException("Peeked at index out of range, must be less than current size of " + String.valueOf(size)+ " , is: " + String.valueOf(index));
        }

        ListNode currentNode = head;

        while (index != 0) {
            currentNode = currentNode.getNext();
            index --;
        }

        return currentNode.getData();

    }

    public Object popFront() {
        Object data = head.getData();
        if (tail == head) {
            tail = null;
            head = null;
        }
        else {
            head = head.getNext();
        }

        size --;

        return data;
    }

    public Object popBack() {
        Object data = head.getData();
        if (tail == head) {
            tail = null;
            head = null;
        }
        else {
            tail = tail.getNext();
        }

        size --;

        return data;
    }
}
