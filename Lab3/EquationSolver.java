package Lab3;

public class EquationSolver {

    private static boolean charIsOperator(char toTest) {
        boolean isOperator = false;
        isOperator |= (toTest == '+');
        isOperator |= (toTest == '-');
        isOperator |= (toTest == '/');
        isOperator |= (toTest == '*');
        isOperator |= (toTest == '(');
        isOperator |= (toTest == ')');

        return isOperator;
    }

    private static boolean charIsDigit(char toTest) {
        boolean isDigit = false;
        isDigit |= (toTest == '0');
        isDigit |= (toTest == '1');
        isDigit |= (toTest == '2');
        isDigit |= (toTest == '3');
        isDigit |= (toTest == '4');
        isDigit |= (toTest == '5');
        isDigit |= (toTest == '6');
        isDigit |= (toTest == '7');
        isDigit |= (toTest == '8');
        isDigit |= (toTest == '9');
        isDigit |= (toTest == '.');

        return isDigit;
    }

    private static int precedenceOfOperator(char operator) {
        int precedence = 0;
        if (operator == '+' || operator == '-') {
            precedence = 1;
        } else if (operator == '*' || operator == '/') {
            precedence = 2;
        }

        return precedence;
    }

    public static double solve(String equation) {
        DSACircularQueue postfix = parseInfixToPostfix(equation);

        double result = evalutatePostfix(postfix);

        return result;

    }

    private static DSACircularQueue parseInfixToPostfix(String equation) {
        char input[] = equation.toCharArray();

        DSACircularQueue output = new DSACircularQueue(input.length);

        DSAStack operators = new DSAStack(input.length);

        String currentNumber = "";

        for (int i = 0; i < input.length; i ++) {
            char currentChar = input[i];

            System.out.println("Current: " + currentChar + " Output: " + output.getString() + " Operators: " + operators.getString());

            //If a digit, extend current number
            if (charIsDigit(currentChar)) {
                currentNumber += currentChar;
            } 
            //If an operator
            else if (charIsOperator(currentChar)) {
                // Push current number onto stack if available
                if (currentNumber != "") {
                    output.push(Double.parseDouble(currentNumber));
                    currentNumber = "";
                } 

                
                if (currentChar == '(') {
                    operators.push('(');
                } else if (currentChar == ')') {
                    boolean shouldContinue = true;
                    while (operators.isEmpty() == false && shouldContinue) {
                        shouldContinue &= (char) operators.peek() != '(';
                        if (shouldContinue) {

                            output.push(operators.pop());
                        }
                    }
                    operators.pop();

                }else {
                    boolean shouldContinue = true;
                    while (operators.isEmpty() == false && shouldContinue) {
                        shouldContinue &= ((char) operators.peek() != '(');
                        shouldContinue &= (precedenceOfOperator(currentChar) <= precedenceOfOperator((char) operators.peek()));

                        if (shouldContinue) {

                            output.push(operators.pop());
   
                        }
                    }

                    operators.push(currentChar);
                }
            
            }

        }

        if (currentNumber != "") {
            output.push(Double.parseDouble(currentNumber));
        }
        while (operators.isEmpty() == false) {
            if ((char) operators.peek() != '(') {

                output.push(operators.pop());
            } else {
                operators.pop();
            }
        }

        return output;
    }

    private static double evalutatePostfix(DSACircularQueue infix) {
        double result = 0;

        DSAStack evaluateStack = new DSAStack(infix.getCurrentSize());

        while (infix.getCurrentSize() != 0) {
            Object newTerm = infix.pop();
            if (newTerm instanceof Double) {
                evaluateStack.push(newTerm);
            } else {
                double b = (double) evaluateStack.pop();
                double a = (double) evaluateStack.pop();

                System.out.println(String.valueOf(a) + " " + newTerm + " " + String.valueOf(b));
                switch ((char) newTerm) {
                    case '+':
                        evaluateStack.push(add(a, b));
                        break;
                    case '-':
                        evaluateStack.push(subtract(a, b));
                        break;
                    case '*':
                        evaluateStack.push(multiply(a, b));
                        break;
                    case '/':
                        evaluateStack.push(divide(a, b));
                        break;
                    default:
                        throw new EquationSolverException("Unexpected operator: " + newTerm);
                }
            }
        }

        if (evaluateStack.getSize() != 1) {
            throw new EquationSolverException("Evaluate stack has wrong number of terms, should be 1, is " + String.valueOf(evaluateStack.getSize()));
        }

        result = (double) evaluateStack.pop();

        return result;
    }

    private static double add(double a, double b) {
        return a + b;
    }

    private static double subtract(double a, double b) {
        return a - b;
    }

    private static double multiply(double a, double b) {
        return a * b;
    }

    private static double divide(double a, double b) {
        return a / b;
    }

    public static void main(String[] args) {
        String infix = "(10.3 * (14 + 3.2)) / (5 + 2 - 4 * 3)";

        // for (int i = 0; i < args.length; i ++) {
        //     infix += args[i];
        // }

        DSACircularQueue postfix = parseInfixToPostfix(infix);

        System.out.println(postfix.getString());

        double result = evalutatePostfix(postfix);
        System.out.println(result);
    }



    
}
