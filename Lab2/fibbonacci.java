package Lab2;


public class fibbonacci {

    private static int getFibbonacci(int n)
    {
        int result;
        if (n == 0) {
            result = 0;
        } else if (n == 1) {
            result = 1;
        } else {
            result = getFibbonacci ( n - 1 ) + getFibbonacci( n - 2);
        }

        return result;
    }

    public static void main(String[] args) {
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
            int result = getFibbonacci(Integer.parseInt(args[0]));

            System.out.println("The fibbonacci number with index of " + String.valueOf(args[0]) + " is " + String.valueOf(result));
        }
    }

}
