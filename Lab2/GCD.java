package Lab2;

//Algorithm from https://www.programiz.com/c-programming/examples/hcf-recursion

public class GCD {
    private static int gcd(int x, int y) {
        int output;
        if (y == 0) {
            output = x;
        } else {
            output = gcd(y, (x % y));
        }

        return output;
    } 

    public static void main(String[] args) {
        int desiredArgumentCount = 2;
        boolean shouldExecute = true;

        try {
            if (args.length != desiredArgumentCount) {
                throw new incorrectArgumentCountException("Should have " + String.valueOf(desiredArgumentCount) + " argument, recieved " + String.valueOf(args.length));
            }


        }  catch (incorrectArgumentCountException e) {
            System.out.println(e.getMessage());
            shouldExecute = false;

        }

        if (shouldExecute) {
            for (int i = 0; i < desiredArgumentCount; i ++) {
                try {
                    Integer.parseInt(args[i]);
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Argument " + String.valueOf(i) + " should be an Integer, instead recieved " + args[i] );
                    shouldExecute = false;
                }
            }
        }

        if (shouldExecute) {
            int result = gcd(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

            System.out.println("The GCD of " + String.valueOf(args[0]) + " and " + String.valueOf(args[1]) + " is " + String.valueOf(result));
        }
        }
}
