package Lab2;


public class factorial {
    private static long factorialRecursive(int n)
    {
        long result;
        if (n == 0)
        {
            result = 1;
        }
        else
        {
            result = n * factorialRecursive(n - 1);
        }

        return result;
    }

    public static void main(String[] args)
    {
        int desiredArgumentCount = 1;
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
            int n = Integer.parseInt(args[0]);

            long result = factorial.factorialRecursive(n);

            System.out.println(String.valueOf(n) + " factorial = " +  String.valueOf(result));
        }
        
    }
}
