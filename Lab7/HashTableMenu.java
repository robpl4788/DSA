package Lab7;

import java.util.Scanner;
import java.io.File;


public class HashTableMenu {
    private static HashTable table = new HashTable();
    public static void main(String[] args) {

        boolean cont = true;
        Scanner scanner;
        File names = new File("Lab7\\RandomNames7000(1).csv");

        String[] inputs ={"hp"};// {"cn a a", "cn b b", "cn c c", "cn d d", "cn e e", "ce a b u", "ce a e u", "ce a d u","ce b e u", "ce b c u", "ce c e u", "ce c d u", "dl"};
        int inputId = -1;

        try {
            scanner = new Scanner(names);

            if (!scanner.hasNextLine()) {
                throw new HashTableMenuException("Name file initialisation failed");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred during initialisation of name file: " + e);
            inputId = 0;
            scanner = new Scanner(System.in);
        }

        
        while (cont) {
            try{
                String input;

            
                if (inputId == -1) {
                    if (scanner.hasNextLine()) {
                        String nameLine = scanner.nextLine();
                        String[] nameLineSplit = nameLine.split(",");
                        input = "ae " + nameLineSplit[0] + " " + nameLineSplit[1]; 

                    } else {
                        inputId = 0;
                        scanner = new Scanner(System.in);
                        input = inputs[inputId];
                        System.out.println(input);
                        inputId ++;
                    }
                } else if (inputId >= inputs.length) {
                    input = scanner.nextLine();
                } else {
                    input = inputs[inputId];
                    System.out.println(input);
                    inputId ++;
                }

                while (input.charAt(input.length() - 1) == ' ') {
                    input = input.substring(0, input.length() - 1);
                }
                

                // System.out.println(input);


                String command = input.substring(0, 2).toLowerCase();


                if (command.equals("hp")) {
                    System.out.println("ae key data      : Add an entry with key and data");
                    System.out.println("gt key           : Display data associated with key");
                    System.out.println("rm key           : Delete entry with key");
                    System.out.println("hk key           : Check if the table has entry with key");
                    System.out.println("dt               : Display hash table");
                    System.out.println("ds               : Display size of hash table");
                    System.out.println("sv               : Save hash table to file");                    
                    System.out.println("hp               : Display this help menu");
                } else if (command.equals("ae")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length < 3) {
                        throw new HashTableMenuException("Invalid input command format, needs 2 arguements, recieved " + (splitInput.length - 1));
                    }
                    String key = splitInput[1];
                    String data = splitInput[2];
                    for (int i = 3; i < splitInput.length; i ++) {
                        data += " ";
                        data += splitInput[i];
                    }

                    table.addElement(key, data);

                } else if (command.equals("gt")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new HashTableMenuException("Invalid input command format, needs 1 arguement, recieved " + (splitInput.length - 1));
                    }
                    String key = splitInput[1];
                    

                    System.out.println(table.getElement(key));
                } else if (command.equals("hk")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new HashTableMenuException("Invalid input command format, needs 1 arguement, recieved " + (splitInput.length - 1));
                    }
                    String key = splitInput[1];
                    

                    System.out.println(table.hasKey(key));
                } else if (command.equals("rm")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new HashTableMenuException("Invalid input command format, needs 1 arguement, recieved " + (splitInput.length - 1));
                    }
                    String key = splitInput[1];
                    table.removeEntry(key);                    
                } else if (command.equals("dt")) {
                    table.printTable();
                } else if (command.equals("ds")) {
                    System.out.println(table.getSize());
                } else if (command.equals("sv")) {
                    table.saveTable();
                } else {
                    throw new HashTableMenuException("Invalid input: " + input + " is not a recognised command");
                }
            } catch (HashTableMenuException e) {
                System.out.println("Exception occurred: " + e);
            }
        }
        
    }
}
