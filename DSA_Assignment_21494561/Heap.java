package DSA_Assignment_21494561;


//Max heap so higher priority number means higher priority
public class Heap {
    public class HeapException extends RuntimeException {
        private HeapException(String s) {
            super(s);
        }
    }

    private class HeapEntry {
        int priority;
        Object data;
        boolean hasData;

        private int getPriority() {
            if (!doesHaveData()) { 
                throw new HeapException("Getting priority from entry that already doesn't have data");
            }
            return priority;
        }

        private Object getData() {
            if (!doesHaveData()) { 
                throw new HeapException("Getting data from entry that already doesn't have data");
            }
            return data;
        }

        private boolean doesHaveData() {
            return hasData;
        }

        private void set(int priority, Object data) {
            if (doesHaveData()) { 
                throw new HeapException("Setting entry that already has data");
            }

            this.priority = priority;
            this.data = data;
            hasData = true;
        }

        private Object pop() {
            Object output = getData();
            hasData = false;
            return output;
        }

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

    public int getSize() {
        return size;
    }

    public Heap(int length) {
        entries = new HeapEntry[length];
        for(int i = 0; i < length; i ++) {
            entries[i] = new HeapEntry();
        }
    
    }

    public void add(int priority, Object data) {
        // System.out.println(priority);
        entries[size].set(priority, data);
        trickleUp(size);
        size ++;
    }

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

    private void trickleUp(int index) {
        if (index != 0) {
            int parentIndex = getParentIndex(index);

            HeapEntry current = entries[index];
            HeapEntry parent = entries[parentIndex];
            // System.out.print("Parent index: ");
            // System.out.println(parentIndex);
            // System.out.println(current.getPriority());
    
            if (current.getPriority() > parent.getPriority()) {
                current.swap(parent);
                trickleUp(parentIndex);
            }
        }
        
    }

    private void trickleDown(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);

        //Left child exists but right doesn't
        if (rightChildIndex == size) {
            HeapEntry current = entries[index];
            HeapEntry leftChild = entries[leftChildIndex];


            if (leftChild.getPriority() > current.getPriority()) {
                leftChild.swap(current);
                // trickleDown(leftChildIndex);
            }

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

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    private int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

}
