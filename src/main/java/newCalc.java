
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Michael
 */
public class newCalc {

    private Scanner scan;
    private ArrayStack stack;
    private ArrayStack operators;
    private String[] splitted, returnValue;
    private String scannedData;
    private DecimalFormat decimal;

    public newCalc() {
        this.stack = new ArrayStack(40);
        this.scan = new Scanner(System.in);
        this.decimal = new DecimalFormat("####0.00");
    }

    public static void main(String[] args) {
        newCalc calc = new newCalc();
        calc.start();
    }

    public void start() {
        RUNNING:
        while (true) {
            boolean checkNumber = false;
            boolean checkOperants = true;
            System.out.println("\n\t\t\t#List of Operators: ( + , - , * , / , ^)");
            System.out.println("\t\t\t#You can write stop at any time to stop the program.");
            scannedData = scan.nextLine();

            splitted = scannedData.trim().split(" ");

            int length = splitted.length;

            for (int i = 0; i < splitted.length; i++) {
                if (splitted[i].trim().equalsIgnoreCase("stop")) {
                    System.out.println("\n\t\t\t#You have stopped the program. Thank you for using the calculator\n\t\tplease come again =)");
                    break RUNNING;
                }
                if (!isOperator(splitted[i].trim()) && !isNumber(splitted[i].trim())) {
                    checkOperants = false;
                    checkNumber = false;
                    System.out.println("\n\t\t\tYou have entered a none number or operant please try again.");
                    break;
                }
            }

            if (splitted.length != 0) {

                OPERANTS:
                while (checkOperants) {
                    if (!isOperator(splitted[length - 1])) {
                        operators = new ArrayStack(0);
                        checkNumber = true;
                        break OPERANTS;
                    }

                    if (isOperator(splitted[length - 1]) && length == 1) {
                        operators = new ArrayStack(1);
                        operators.push(splitted[length - 1].trim());
                        System.out.println("stack:");
                        printStack(stack, stack.size());
                        break OPERANTS;
                    } else {
                        while (true) {

                            if (isOperator(splitted[0]) && length == 0) {
                                operators = new ArrayStack(splitted.length);
                                for (int i = 0; i < operators.size(); i++) {
                                    operators.push(splitted[i].trim());
                                }
                                checkNumber = false;
                                System.out.println("stack:");
                                printStack(stack, stack.size());
                                break OPERANTS;
                            }
                            if (!isOperator(splitted[length - 1]) && length != 0) {
                                operators = new ArrayStack(splitted.length - length);
                                for (int i = operators.size(); i < splitted.length; i++) {
                                    if (isOperator(splitted[i])) {
                                        operators.push(splitted[i].trim());
                                    }
                                }
                                checkNumber = true;
                                break OPERANTS;
                            } else if (length != 0) {
                                length--;
                            }
                        }
                    }
                }
                NUMBER:
                while (checkNumber) {
                    double result = 0;

                   
                    if (!isNumber(splitted[length - 1]) && length != 0) {
                        System.out.println("\ntypo please - try again");
                        break NUMBER;
                    }

                    if (isNumber(splitted[length - 1])) {
                        for (int i = 0; i < splitted.length; i++) {
                            if (isNumber(splitted[i])) {
                                stack.push(splitted[i].trim());
                            }
                        }

                        if (operators.size() >= stack.size()) {
                            System.out.println("\t\t\t#You have too many operants - please try again.\n");
                            break NUMBER;
                        } else if (operators.isEmpty()) {
                            System.out.println("\nstack:");
                            printStack(stack, stack.size());
                            break NUMBER;
                        } else {

                            while (true) {

                                while (operators.peek() != null && stack.size() >= 2) {

                                    String operator = (String) operators.pop();
                                    double num1 = Double.parseDouble(stack.pop().toString());
                                    double num2 = Double.parseDouble(stack.pop().toString());

                                    result = calculateIt(num2, num1, operator);
                                    stack.push(result);

                                    System.out.println("\n\t\t#calculating: "
                                            + num1 + " " + operator + " " + num2 + " = " + result);

                                    System.out.println("stack:");

                                    if (operators.size() != 0) {
                                    printOperants(operators, operators.size());
                                    }
                                        printStack(stack, stack.size());

                                }
                                break NUMBER;
                            }
                        }
                    }
                }
            }
        }
    }

    private void printOperants(ArrayStack operators, int operatorSize) {
        for (int i = 0; i < operatorSize; i++) {
            System.out.println(operators.print(i));
        }
    }

    private void printStack(ArrayStack stack, int stackSize) {
        for (int i = 0; i < stackSize; i++) {
            if (stack.print(i) != null) {

                System.out.println(stack.print(i));
            }
        }
    }

    private double calculateIt(double num1, double num2, String operator) {

        switch (operator.trim()) {
            case "-":
                returnValue = decimal.format(num1 - num2).split(",");
                return Double.parseDouble(returnValue[0] + "." + returnValue[1]);
            case "+":
                returnValue = decimal.format(num1 + num2).split(",");
                return Double.parseDouble(returnValue[0] + "." + returnValue[1]);
            case "/":
                returnValue = decimal.format(num1 / num2).split(",");
                return Double.parseDouble(returnValue[0] + "." + returnValue[1]);
            case "*":
                returnValue = decimal.format(num1 * num2).split(",");
                return Double.parseDouble(returnValue[0] + "." + returnValue[1]);
            case "^":
                returnValue = decimal.format(Math.pow((double) num1, (double) num2)).split(",");
                return Double.parseDouble(returnValue[0] + "." + returnValue[1]);
            default:
                return 0;
        }
    }

    public static boolean isNumber(String string) {

        if (string.trim() == "0") {
            return true;
        }
        try {
            Double.parseDouble(string.trim());
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isOperator(String s) {
        if (s.equals(" ")) {
            return false;
        }
        String[] ops = {"+", "-", "*", "/", "^"};
        for (int i = 0; i < ops.length; i++) {
            if (s.trim().equalsIgnoreCase(ops[i])) {
                return true;
            }
        }
        return false;
    }
}
