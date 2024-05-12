package Lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Lab7.HashTableMenuException;

public class HeapTest {
    public static void main(String[] args) {
        Scanner scanner;
        File names = new File("Lab7\\RandomNames7000(1).csv");
        try {

            for (int toRead = 2; toRead <= 7000; toRead ++) {
                boolean shouldContinue = true;

                Heap heap = new Heap(toRead);
    
        
    
                scanner = new Scanner(names);
    
                if (!scanner.hasNextLine()) {
                    scanner.close();

                    throw new HashTableMenuException("File initialisation failed");
                }
                
                int limit = toRead;
                while (shouldContinue) {
                    if (scanner.hasNextLine()) {
                        int n = Integer.parseInt(scanner.nextLine().split(",")[0]);
                        heap.add(n, n);
                        
                    } else {
                        shouldContinue = false;
                    }
    
                    limit --;
                    if (limit == 0) {
                        shouldContinue = false;
                    }
                }
    
                int[] sorted = new int[toRead];
    
                int i = toRead - 1;
    
                while (heap.getSize() != 0) {
                    sorted[i] = (int) heap.pull();
                    // System.out.println(sorted[i]);
                    i --;
                }
    
                boolean valid = true;
                for (int j = 1; j < toRead; j ++) {
                    if (sorted[j - 1] > sorted[j]) {
                        // System.out.print(sorted[j]);
                        // System.out.print(" is not sorted correctly at index ");
                        // System.out.println(j);
                        valid = false;
                    }
                }
    
                if (valid) {
                    System.out.print(toRead);
                    System.out.println(": Correctly sorted");
                } else {
                    System.out.print(toRead);
                    System.out.println(": Not sorted lol :(");
                    // throw new Exception("AHHH");
                }
        }

        
        } catch (FileNotFoundException e) {
            System.out.println("Exception occurred during initialisation of file: " + e);
            e.printStackTrace();
        }

        
    }
    
}
