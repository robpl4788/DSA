package DSA_Assignment_21494561.DataTypes;


//Max heap so higher priority number means higher priority
public class Heap {
    public class HeapException extends RuntimeException {
        private HeapException(String s) {
            super(s);
        }
    }

    // Class to store an entry in the heap
    private class HeapEntry {
        int priority;
        Object data;
        boolean hasData;

        // Get the priority of an entry
        private int getPriority() {
            if (!doesHaveData()) { 
                throw new HeapException("Getting priority from entry that already doesn't have data");
            }
            return priority;
        }

        // Get the data of an entry
        private Object getData() {
            if (!doesHaveData()) { 
                throw new HeapException("Getting data from entry that already doesn't have data");
            }
            return data;
        }

        private boolean doesHaveData() {
            return hasData;
        }

        // Set the data and priority of an entry
        private void set(int priority, Object data) {
            if (doesHaveData()) { 
                throw new HeapException("Setting entry that already has data");
            }

            this.priority = priority;
            this.data = data;
            hasData = true;
        }

        // Delete an entry, returning the data it had, doesn't actually delete just forgets it has data
        private Object pop() {
            Object output = getData();
            hasData = false;
            return output;
        }

        // Swap the data and priority with a different entry
        private void swap(HeapEntry other) {
            int tempPriority = priority;
            Object tempData = data;
            boolean tempHasData = hasData;

            priority = other.priority;
            data = other.data;
            hasData = other.hasData;

            other.priority = tempPriority;
            other.data = tempData;
            other.hasData = tempHasData;
        }
        
    }


    HeapEntry[] entries;
    int size = 0;

    // Get the size of the heap
    public int getSize() {
        return size;
    }

    // Initialise the heap with a max size of the given value
    public Heap(int length) {
        entries = new HeapEntry[length];
        for(int i = 0; i < length; i ++) {
            entries[i] = new HeapEntry();
        }
    
    }

    // Add an entry to the heap with given priority and data
    public void add(int priority, Object data) {
        entries[size].set(priority, data);
        trickleUp(size);
        size ++;
    }

    // Pull an entry of the heap, deleting it, rearranging the heap to maintain validity and returning the result
    public Object pull() {
        if (size == 0) {
            throw new HeapException("Pulling from empty heap");
        }
        size --;

        Object result = entries[0].pop();
        entries[0].swap(entries[size]);
        trickleDown(0);
        return result;
    }

    
    // Move an entry up the list if it's in the wrong position, repeating as needed
    private void trickleUp(int index) {
        if (index != 0) {
            int parentIndex = getParentIndex(index);

            HeapEntry current = entries[index];
            HeapEntry parent = entries[parentIndex];
    
            if (current.getPriority() > parent.getPriority()) {
                current.swap(parent);
                trickleUp(parentIndex);
            }
        }
        
    }

    // Move an entry down the list if it's in the wrong position, repeating as needed
    private void trickleDown(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);

        //Left child exists but right doesn't
        if (rightChildIndex == size) {
            HeapEntry current = entries[index];
            HeapEntry leftChild = entries[leftChildIndex];


            if (leftChild.getPriority() > current.getPriority()) {
                leftChild.swap(current);
            }

        // Left and right child exist
        } else if (size > rightChildIndex) {
            HeapEntry current = entries[index];
            HeapEntry leftChild = entries[leftChildIndex];
            HeapEntry rightChild = entries[rightChildIndex];

            //Left is greatest of the three or same as right and greater than parent
            if (current.getPriority() < leftChild.getPriority() && rightChild.getPriority() <= leftChild.getPriority()) {
                leftChild.swap(current);
                trickleDown(leftChildIndex);
            //Right is greatest of the three
            } else if (current.getPriority() < rightChild.getPriority() && leftChild.getPriority() < rightChild.getPriority()) {
                rightChild.swap(current);
                trickleDown(rightChildIndex);
            }
        }

    }

    // Get the index of the parent of an entry with a given index
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    // Get the index of the left child of an entry with a given index
    private int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    // Get the index of the right of an entry with a given index
    private int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

}
