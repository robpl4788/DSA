package DSA_Assignment_21494561.DataTypes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class HashTable {
    public class HashTableException extends RuntimeException {
        public HashTableException(String s) {
            super(s);
        }
    }
    
    private class HashTableEntry {
        String key;
        Object data;

        boolean initialised = false;
        boolean hasData = false;
        
        
        private boolean isInitialised() {
            return initialised;
        }

        private boolean doesHaveData() {
            return hasData;
        }

        public void set(String Key, Object Data) {
            key = Key;
            data = Data;
            initialised = true;
            hasData = true;
        }
        
        public void delete() {
            if (hasData == false) {
                throw new HashTableException("Deleting HashTableEntry that doesn't exist");
            }
            hasData = false;
        }

        private String getKey() {
            return key;
        }

        private Object getData() {
            return data;
        }
    }

    //Config parameters
    private int minLength = 11;
    private double minCapacity = 0.1;
    private double maxCapacity = 0.5;
    private double targetCapacity = (minCapacity + maxCapacity) / 2;
    private double targetMaxStepFraction = 0.25;

    private int tableLength = minLength;

    private int size = 0;
    private int entries = 0;

    private HashTableEntry[] table = new HashTableEntry[tableLength];

    private int maxStep = 3;

    public HashTable() {
        for (int i = 0; i < tableLength; i ++) {
            table[i] = new HashTableEntry();
        }

        // for (int i = 0; i < 100; i ++) {
        //     System.out.println(Integer.toString(i) + ": " + Integer.toString(nextPrime(i)));
        // }
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

    private void resize() {
        if (((double) size) / tableLength < minCapacity || ((double) Math.max(size, entries)) / tableLength > maxCapacity) {
            // System.out.print("Resizing from ");
            // System.out.print(tableLength);
            // System.out.print(" to ");
            HashTableEntry[] oldTable = table;

            tableLength = nextPrime((int)(size / targetCapacity));
            if (tableLength < minLength) {
                tableLength = minLength;
            }

            // System.out.println(tableLength);
            maxStep = nextPrime((int)(tableLength * targetMaxStepFraction));
    
            table = new HashTableEntry[tableLength];
            for (int i = 0; i < tableLength; i ++) {
                table[i] = new HashTableEntry();
            }
    
            size = 0;
            entries = 0;
    
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
                        // System.out.println(index);
                    }
                    table[index].set(current.getKey(), current.getData());
                    
                    size ++;
                    entries ++;
                }
            }
        }
        

    }

    private int hash(String toHash) {
        int hashIndex = 0;

        for (int i = 0; i < toHash.length(); i ++) {
            hashIndex = (hashIndex * 33) + toHash.charAt(i);
        }

        hashIndex = Math.abs(hashIndex);

        hashIndex %= getTableLength();
        return hashIndex;
    }

    private int stepHash(String toHash) {
        // System.out.println(maxStep);
        return hash(toHash) % (maxStep - 1) + 1;
    }

    public void addElement(String key, Object data) {

        if (size >= getTableLength()) {
            throw new HashTableException("Hash table is full");
        }

        int index = hash(key);
        int step = stepHash(key);
        // System.out.println(key);
        // System.out.println(tableLength);
        // System.out.println(index);
        // System.out.println(table[index]);

        try {
            while (table[index].doesHaveData()) {
                // System.out.println(index);
                // System.out.println(step);
                if (table[index].getKey().equals(key)) {
                    throw new HashTableException("Key already exists in table, not adding entry");
                }
                
                index += step;
                index %= getTableLength();
    
                
                // System.out.println(index);
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

    public Object getElement(String key) {
        return getEntry(key).getData();
    }

    public void removeEntry(String key) {
        getEntry(key).delete();
        size --;

        resize();
    }

    public boolean hasKey(String key) {
        boolean result = true;

        try {
            getElement(key);
        } catch (HashTableException e) {
            result = false;
        }

        return result;
    }

    public void saveTable() {
        File out = new File("Lab7\\out.csv");

        try {
            if (!out.createNewFile()) {
                out.delete();
            }
            FileWriter myWriter = new FileWriter("Lab7\\out.csv");

            for (int i = 0; i < tableLength; i ++) {
                String output = "";
                HashTableEntry current = table[i];

                if (current.doesHaveData()) {
                    output += current.getKey();
                    output += ",";
                    output += current.getData();
                    output += "\n";


                    myWriter.write(output);
                }
            }

            myWriter.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
