package Lab5;

import java.util.Scanner;

public class TreeMenu {

    private static Tree tree = new Tree();
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        String[] inputs = {"i q q", "i w w", "i e e", "i r r", "i t t", "i y y", "i u u", "i o o", "i p p", "d tree", "h"};
        int inputId = 0;
        while (true) {
            String input;

            if (inputId >= inputs.length) {
                input = scanner.nextLine();
            } else {
                input = inputs[inputId];
                inputId ++;
            }

            while (input.charAt(input.length() - 1) == ' ') {
                input = input.substring(0, input.length() - 1);
            }


            String command = input.substring(0, 1).toLowerCase();


            if (command.equals("h")) {
                System.out.println("i key data          : Insert node with key and data");
                System.out.println("r key               : Remove node with key");
                System.out.println("d tree/in/pre/post  : Display kys as a tree/in order/pre-order/post-order");
                System.out.println("h                   : Display this help menu");
            } else if (command.equals("i")) {
                String[] splitInput = input.split(" ");
                if (splitInput.length < 3) {
                     throw new TreeMenuException("Invalid input command format, needs 2 arguements, recieved 1");
                }
                String key = splitInput[1];
                String data = "";
                for (int i = 2; i < splitInput.length; i ++) {
                    data += splitInput[i];
                }

                tree.insert(key, data);
            } else if (command.equals("r")) {
                String[] splitInput = input.split(" ");

                try {
                    tree.delete(splitInput[1]);
                } catch (TreeException e) {
                    System.out.println(e);
                }
            } else if (command.equals("d")) {
                String[] splitInput = input.split(" ");

                if (splitInput[1].equals("tree")) {
                    tree.display();
                } else if (splitInput[1].equals("in")) {
                    System.out.println(tree.keysInOrder());
                } else if (splitInput[1].equals("pre")) {
                    System.out.println(tree.keysPreOrder());
                } else if (splitInput[1].equals("post")) {
                    System.out.println(tree.keysPostOrder());
                } else {
                    throw new TreeMenuException("display command arguement must be one of (tree, in, pre, post) is: " + splitInput[1]);
                }

            }else {
                throw new TreeMenuException("Invalid input: " + input + " is not a recognised command");
            }

        }
    }
}
