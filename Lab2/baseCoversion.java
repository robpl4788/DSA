package Lab2;

public class baseCoversion {
    private static String convertBase(int n, int newBase) {
        String result;

        if (n == 0) {
            result = "";
        } else {
            result = convertBase(n / newBase, newBase) + convertNumberToHex(n % newBase);
        }

        return result;
    }

    private static String  convertNumberToHex (int n) {
        String result  = "L Sucker :)";

        if (n < 10) {
            result = String.valueOf(n);
        } else {
            switch (n) {
                case 10:
                    result = "a";
                    break;
                case 11:
                    result = "b";
                    break;
                case 12:
                    result = "c";
                    break;
                case 13:
                    result = "d";
                    break;
                case 14:
                    result = "e";
                    break;
                case 15:
                    result = "f";
                    break;
                default:
                    break;
            }
        }

        return result;
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
            String result = convertBase(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

            System.out.println(args[0] + " in base " + args[1] + " is " + result);
        }
    }
}
