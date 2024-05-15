package DSA_Assignment_21494561;

import java.util.Scanner;

import DSA_Assignment_21494561.RouteFinder.Router;
import Lab6.GraphMenuException;

public class RouteFinderMenu {
    public static void main(String[] args) {
        Router routeFinder = new Router();
        
        
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        String[] inputs ={};
        int inputId = 0;
        while (true) {
            try{
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


                String command = input.substring(0, 4).toLowerCase();


                if (command.equals("help")) {
                    System.out.println("aprt code              : Display information about the airport with 3 letter IATA code");
                    System.out.println("rout from to depth d/l : Display all routes with a max layover count of depth, between the airports with IATA codes from and to sorted by layovers(l) or distance(d)");
                    System.out.println("help                   : Display this help menu");
                } else if (command.equals("aprt")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new GraphMenuException("Invalid input command format, needs 1 arguements, recieved " + (splitInput.length - 1));
                    }

                    routeFinder.printAirportInfo(splitInput[1]);

                    // routeFinder
                } else if (command.equals("rout")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 5) {
                        throw new GraphMenuException("Invalid input command format, needs 4 arguements, recieved " + (splitInput.length - 1));
                    }

                    if (splitInput[4].equals("d")) {
                        routeFinder.printAllRoutes(splitInput[1], splitInput[2], Integer.valueOf(splitInput[3]), true);
                    } else if (splitInput[4].equals("l")) {
                        routeFinder.printAllRoutes(splitInput[1], splitInput[2], Integer.valueOf(splitInput[3]), false);
                    } else {
                        throw new GraphMenuException("Invalid input arguement 4, expected d or l, recieved " + (splitInput[4]));
                    }
                } else {
                    throw new GraphMenuException("Invalid input: " + input + " is not a recognised command");
                }
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e);
            }
        }   
    }
    
}
