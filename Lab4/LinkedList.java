package Lab4;
public class LinkedList {
    private ListNode head = null;
    private ListNode tail = null;
    private int size = 0;
    private ListNode iterator = null;

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void pushFront(Object data) {
        ListNode newHead = new ListNode(data, head, null);
        // System.out.println(":)");
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

    public void pushIndex(int index, Object data) {
        if (index < 0) {
            throw new LinkedListException("Pushed at index out of range, must be greater than or equal to 0, is: " + String.valueOf(index));
        } else if (index > size) {
            throw new LinkedListException("Pushed at index out of range, must be less than current size of " + String.valueOf(size)+ " , is: " + String.valueOf(index));
        }

        // System.out.println(index);
        if (index > 0 && index < size) {

            ListNode newNode = new ListNode(data);

            ListNode currentNode = head;


            while (index != 0) {
                currentNode = currentNode.getNext();
                index --;
            }
    
            
            // System.out.println(currentNode == null);


            newNode.setNext(currentNode);
            newNode.setPrev(currentNode.getPrev());

            currentNode.getPrev().setNext(newNode);
            currentNode.setPrev(newNode);
          

            size ++;
        } else if (index == 0) {
            // System.out.println(":()");
            pushFront(data);
        } else if (index == size) {
            pushBack(data);
        } else {
            throw new LinkedListException("Im so confident this won't happen");
        }

        
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
            head.setPrev(null);
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
            tail = tail.getPrev();
            tail.setNext(null);
        }

        size --;

        return data;
    }

    public void setIteratorAtHead() {
        iterator = head;
    }

    public void setIteratorAtTail() {
        iterator = tail;
    }

    //returns true if still in the list
    public boolean setIteratorNext() {
        if (iterator != null) {
            iterator = iterator.getNext();
        }
        if (iterator == null) {
            return false;
        } else {
            return true;
        }
    }

    //returns true if still in the list
    public boolean setIteratorPrev() {
        if (iterator != null) {
            iterator = iterator.getPrev();
        }
        if (iterator == null) {
            return false;
        } else {
            return true;
        }
    }

    public Object getIteratorData() {
        Object result = null;
        if (iterator != null) {
            result = iterator.getData();
        }
        return result;
    }

    public Object popIteratorNode() {
        Object toDelete = null;
        if (iterator == head) {
            toDelete = popFront();
        } else if (iterator == tail) {
            toDelete = popBack();
        } else {
            toDelete = iterator.getData();
            iterator.getPrev().setNext(iterator.getNext());
            iterator.getNext().setPrev(iterator.getPrev());
        }
        
        iterator = null;
        return toDelete;
    }
}
