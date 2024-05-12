package Lab8;

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
            if (doesHaveData()) { 
                throw new HeapException("Getting priority from entry that already doesn't have data");
            }
            return priority;
        }

        private Object getData() {
            if (doesHaveData()) { 
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
            hasData = false;
            return data;
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
    
}
