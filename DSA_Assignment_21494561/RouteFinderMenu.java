package DSA_Assignment_21494561;

import java.util.Scanner;

import DSA_Assignment_21494561.RouteFinder.Router;

public class RouteFinderMenu {

    
    public static class RouterFinderException extends RuntimeException {
        private RouterFinderException(String s) {
            super(s);
        }
    }


    public static void main(String[] args) {
        Router routeFinder = new Router();
        
        
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        String[] inputs ={};
        int inputId = 0;

        routeFinder.sortComparison();
        while (true) {
            try{
                String input;

                System.out.println("aprt {code}                    : Display information about the airport with 3 letter IATA {code}");
                System.out.println("rout {from} {to} {depth} {d/l} : Display the 20 best routes with a max layover count of {depth}, between the airports with IATA codes {from} and {to} sorted by layovers{l} or distance{d}");

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


                if (command.equals("aprt")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 2) {
                        throw new RouterFinderException("Invalid input command format, needs 1 arguements, recieved " + (splitInput.length - 1)
                            + "\nTry: aprt PER");
                    }

                    routeFinder.printAirportInfo(splitInput[1].toUpperCase());

                    // routeFinder
                } else if (command.equals("rout")) {
                    String[] splitInput = input.split(" ");
                    if (splitInput.length != 5) {
                        throw new RouterFinderException("Invalid input command format, needs 4 arguements, recieved " + (splitInput.length - 1) + "\nTry: rout PER SYD 2 d");
                    }

                    if (splitInput[4].equals("d")) {
                        routeFinder.printRoutes(splitInput[1].toUpperCase(), splitInput[2].toUpperCase(), Integer.valueOf(splitInput[3]) + 1, true);
                    } else if (splitInput[4].equals("l")) {
                        routeFinder.printRoutes(splitInput[1].toUpperCase(), splitInput[2].toUpperCase(), Integer.valueOf(splitInput[3]) + 1, false);
                    } else {
                        throw new RouterFinderException("Invalid input arguement 4, expected d or l, recieved " + (splitInput[4]));
                    }
                } else {
                    throw new RouterFinderException("Invalid input: " + input + " is not a recognised command");
                }
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e);
            }
        }   
    }
    
}
