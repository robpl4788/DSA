package DSA_Assignment_21494561.DataTypes;


import java.lang.Math;
import java.util.Random;

public class HashTable {
    public class HashTableException extends RuntimeException {
        public HashTableException(String s) {
            super(s);
        }
    }
    
    // Class to store each entry in the hash table
    private class HashTableEntry {
        String key;
        Object data;


        boolean initialised = false; // Has this node had data in the past?
        boolean hasData = false;     // Does this node have data currently
        
        
        private boolean isInitialised() {
            return initialised;
        }

        private boolean doesHaveData() {
            return hasData;
        }

        // Give the node data and a key
        public void set(String Key, Object Data) {
            key = Key;
            data = Data;
            initialised = true;
            hasData = true;
        }
        
        // Delete the data from the node (doesn't actually delete just forgets that it exists)
        public void delete() {
            if (hasData == false) {
                throw new HashTableException("Deleting HashTableEntry that doesn't exist");
            }
            hasData = false;
        }

        // Get the key to this node, throws an error if it doesn't have data
        private String getKey() {
            if (hasData == false) {
                throw new HashTableException("Getting key from HashTableEntry that doesn't exist");
            }
            return key;
        }

        // Get the data from this node, throws an error if it doesn't have data
        private Object getData() {
            if (hasData == false) {
                throw new HashTableException("Getting data from HashTableEntry that doesn't exist");
            }
            return data;
        }
    }

    //Config parameters
    private int minLength = 11; // Min length the hashtable should be
    private double minCapacity = 0.1; // Fraction of fullnes at which the table should downsize
    private double maxCapacity = 0.5; // Fraction of fullnes at which the table should upsize
    private double targetCapacity = (minCapacity + maxCapacity) / 2; // Capacity fraction to resize to
    private double targetMaxStepFraction = 0.25; // Fraction of the table the maximum linear probe step should be
    private int maxStep = 3; // The Maximum linear probe step size

    private int tableLength = minLength; // The current length of the table

    private int size = 0; // The current number of active entries
    private int entries = 0; // The current number of active and deleted entries

    private HashTableEntry[] table = new HashTableEntry[tableLength];


    public HashTable() {
        for (int i = 0; i < tableLength; i ++) {
            table[i] = new HashTableEntry();
        }
    }

    public int getSize() {
        return size;
    }

    private int getTableLength() {
        return tableLength;
    }
    
    //Returns the prime after value
    private int nextPrime(int value) {
        int result = value - 1;

        if (result < 1) {
            result = 1;
        }

        boolean isPrime = false;

        while (isPrime == false) {
            result ++;
            isPrime = true;

            // Check if result is a prime
            int maxFactor = (int) Math.sqrt(result);
            for (int i = 2; i <= maxFactor; i ++) {
                if (result % i == 0) {
                    isPrime = false;
                    i = maxFactor + 1;
                }   
            }
        }

        return result;
    }

    // Attempt to resize the table, will not if should not
    private void resize() {
        // Check if should resize the table
        if (((double) size) / tableLength < minCapacity || ((double) Math.max(size, entries)) / tableLength > maxCapacity) {

            HashTableEntry[] oldTable = table;

            tableLength = nextPrime((int)(size / targetCapacity));
            if (tableLength < minLength) {
                tableLength = minLength;
            }

            maxStep = nextPrime((int)(tableLength * targetMaxStepFraction));
    
            // Create the new table
            table = new HashTableEntry[tableLength];
            for (int i = 0; i < tableLength; i ++) {
                table[i] = new HashTableEntry();
            }
    
            size = 0;
            entries = 0;
    
            // Insert all the old elements into the new table
            for (int i = 0; i < oldTable.length; i ++) {
                HashTableEntry current = oldTable[i];
                if (current.doesHaveData()) {
                    if (size >= getTableLength()) {
                        throw new HashTableException("Hash table is full while resizing, this really shouldn't happen");
                    }
            
                    int index = hash(current.getKey());
                    int step = stepHash(current.getKey());

                    while (table[index].doesHaveData()) {
                        index += step;
                        index %= getTableLength();
                    }

                    table[index].set(current.getKey(), current.getData());
                    
                    size ++;
                    entries ++;
                }
            }
        }
        

    }

    // Hash a string into an integer to be used as an index into the table
    private int hash(String toHash) {
        int hashIndex = 0;

        for (int i = 0; i < toHash.length(); i ++) {
            hashIndex = (hashIndex * 33) + toHash.charAt(i);
        }

        hashIndex = Math.abs(hashIndex);

        hashIndex %= getTableLength();
        return hashIndex;
    }

    // Hash a string into an integer to be used to linearly probe the table
    private int stepHash(String toHash) {
        return hash(toHash) % (maxStep - 1) + 1;
    }

    // Add an element to the table
    public void addElement(String key, Object data) {

        if (size >= getTableLength()) {
            throw new HashTableException("Hash table is full");
        }

        int index = hash(key);
        int step = stepHash(key);

        // Add an index if it's key doesn't already exist, if it does print an error
        try {
            while (table[index].doesHaveData()) {

                if (table[index].getKey().equals(key)) {
                    throw new HashTableException("Key already exists in table, not adding entry");
                }
                
                index += step;
                index %= getTableLength();
    
                            }
            if (table[index].isInitialised() == false) {
                entries ++;
            }

            table[index].set(key, data);
            size ++;


            resize();
    
        } catch (HashTableException e) {
            System.out.println(e + ": Key is: " + key + " Data is: " + data);
        }
        
        
    }

    // Print the table to the terminal
    public void printTable() {
        for(int i = 0; i < getTableLength(); i ++) {
            if (table[i].doesHaveData()) {
                System.out.print(table[i].getKey());
            } else {
                System.out.print("_");
            }
            System.out.print(", ");
        }
        System.out.println("\n");
    }

    // Get the table entry with a given key
    private HashTableEntry getEntry(String key) {
        HashTableEntry result = null;

        int index = hash(key);
        int step = stepHash(key);
        int count = 0;

        while (result == null) {
            HashTableEntry current = table[index];
            if (current.isInitialised()) {
                if (current.getKey().equals(key)) {
                    result = current;
                } else {
                    index += step;
                    index %= getTableLength();
                }
            } else {
                throw new HashTableException("Getting object that does not exist: " + key);
            }

            count ++;
            if (count == getTableLength()) {
                throw new HashTableException("Getting object that does not exist: " + key);
            } 
        }
        return result;
    }

    // Get the data attached to an entry with a given key
    public Object getElement(String key) {
        return getEntry(key).getData();
    }

    // Delete an entry with a given key
    public void removeEntry(String key) {
        getEntry(key).delete();
        size --;

        resize();
    }

    // Check if a node with a given key is in the table
    public boolean hasKey(String key) {
        boolean result = true;

        try {
            getElement(key);
        } catch (HashTableException e) {
            result = false;
        }

        return result;
    }

    // Get the data attached to a random entry in the table
    public Object randomEntry() {
        Random rand = new Random();

        int i = rand.nextInt(tableLength);

        while (table[i].hasData == false) {
            i ++;
            if (i >= tableLength) {
                i = 0;
            }
        }

        Object result = table[i].getData();

        return result;
    }
}
