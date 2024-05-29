package DSA_Assignment_21494561.DataTypes;

public class LinkedList {
    public class LinkedListException extends RuntimeException {
        public LinkedListException(String s) {
            super(s);
        }
    }

    // Object to hold the data and point to other nodes
    public class ListNode {
        private Object m_data;
        private ListNode m_next;
        private ListNode m_prev;
    
        // Create a node pointing at two other nodes
        public ListNode(Object data, ListNode next, ListNode prev) {
            m_data = data;
            m_next = next;
            m_prev = prev;
        }
    
        // Create a node pointing at no other nodes
        public ListNode(Object data) {
            m_data = data;
            m_next = null;
            m_prev = null;
        }
            public void setData(Object data) {
            m_data = data;
        }
    
        public Object getData() {
            return m_data;
        }
    
        public void setNext(ListNode next) {
            m_next = next;
        }
    
        public ListNode getNext() {
            return m_next;
        }
    
        public void setPrev(ListNode prev) {
            m_prev = prev;
        }
    
        public ListNode getPrev() {
            return m_prev;
        }
    }

    private ListNode head = null; // First node in the list
    private ListNode tail = null; // Last node in the list
    private int size = 0; // number of nodes in the list
    private ListNode iterator = null; // Reference to an iterator which is always at a node, and is expected to move throught the list scanning it

    //Will create a linked list withthe same data in the same order as copy
    public LinkedList (LinkedList copy) {
        copy.setIteratorAtHead();
        do {
            pushBack(copy.getIteratorData());
        } while (copy.setIteratorNext());
    }
    
    // Create a new empty linked list
    public LinkedList () {}

    // Check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the size of the list
    public int getSize() {
        return size;
    }

    // Push an entry to the front of the list
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

    // Push an entry to the back of the list 
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

    // Insert a node at a given index
    public void pushIndex(int index, Object data) {
        if (index < 0) {
            throw new LinkedListException("Pushed at index out of range, must be greater than or equal to 0, is: " + String.valueOf(index));
        } else if (index > size) {
            throw new LinkedListException("Pushed at index out of range, must be less than current size of " + String.valueOf(size)+ " , is: " + String.valueOf(index));
        }

        if (index > 0 && index < size) {

            ListNode newNode = new ListNode(data);

            ListNode currentNode = head;


            while (index != 0) {
                currentNode = currentNode.getNext();
                index --;
            }
    

            newNode.setNext(currentNode);
            newNode.setPrev(currentNode.getPrev());

            currentNode.getPrev().setNext(newNode);
            currentNode.setPrev(newNode);
          

            size ++;
        } else if (index == 0) {
            pushFront(data);
        } else if (index == size) {
            pushBack(data);
        } else {
            throw new LinkedListException("Im so confident this won't happen");
        }
    }

    // Get the data from the node at the front without deleting it
    public Object peekFront() {
        return head.getData();
    }

    // Get the data from the node at the back without deleting it
    public Object peekBack() {
        return tail.getData();
    }

    // Get the data from the node at the given index without deleting it
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

    // Get the data from the node at the front and delete it
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

    // Get the data from the node at the back and delete it
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

    // Set the iterator to be referencing the head of the list
    public void setIteratorAtHead() {
        iterator = head;
    }

    // Set the iterator to be referencing the tail of the list
    public void setIteratorAtTail() {
        iterator = tail;
    }

    // Move the iterator one step towards the end of the list, returns true if still in the list
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

    // Move the iterator one step towards the start of the list, returns true if still in the list
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

    // Get the data from the entry the iterator is currently pointing at
    public Object getIteratorData() {
        Object result = null;
        if (iterator != null) {
            result = iterator.getData();
        }
        return result;
    }

    // Delete the node the iterator is currently pointing at, will set iterator to null
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

    // Get the Linked list as an array of objects, will copy references to each object
    public Object[] asArray() {
        if (size == 0) {
            throw new LinkedListException("Getting linked list as an array");
        }


        Object[] array = new Object[size];

        setIteratorAtHead();
        int i = 0;
        do {
            array[i] = getIteratorData();
            i ++;
        } while (setIteratorNext());


        return array;
    }
}
