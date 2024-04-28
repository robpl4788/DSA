package Lab4;

import java.util.Scanner;

public class InteractiveMenu {

    private static LinkedList list = new LinkedList();

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            while (input.charAt(input.length() - 1) == ' ') {
                input = input.substring(0, input.length() - 1);
            }


            String command = input.substring(0, 2).toLowerCase();


            if (command.equals("hp")) {
                System.out.println("if list: Insert first");
                System.out.println("rf list: Remove first");
                System.out.println("pf list: Peek first");
                System.out.println("il list: Insert last");
                System.out.println("rl list: Remove last");
                System.out.println("pl list: Peek last");
                System.out.println("hp: display this");
                System.out.println("ds: Display List");

            } else if (command.equals("if") && input.length() >= 4) {
                list.pushFront(input.substring(3));
            } else if (command.equals("rf")) {
                if (list.getSize() == 0) {
                    System.out.println("Nothing to remove");
                } else {
                    list.popFront();
                }
            } else if (command.equals("pf")) {
                if (list.getSize() == 0) {
                    System.out.println("Nothing to see");
                } else {
                    System.out.println(list.peekFront());
                }
            } else if (command.equals("il")  && input.length() >= 4) {
                list.pushBack(input.substring(3));
            } else if (command.equals("rl")) {
                if (list.getSize() == 0) {
                    System.out.println("Nothing to remove");
                } else {
                    list.popBack();
                }
            } else if (command.equals("pl")) {
                if (list.getSize() == 0) {
                    System.out.println("Nothing to see");
                } else {
                    System.out.println(list.peekBack());
                }
            } else if (command.equals("ds")) {
                int listSize = list.getSize();
                if (listSize == 0) {
                    System.out.println("List is empty");
                }
                for (int i = 0; i < listSize; i ++) {
                    System.out.println(list.peekIndex(i));
                }
            } else {
                System.out.println("Invalid input: " + input + " is not a recognised command");
            }

        }

    
    }
    
}
