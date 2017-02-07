import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Michael
 */
public class Calculator {

    private Scanner scan;
    private List<Integer> stack;
    private List<String> operators;
    private String[] splitted;
    private String scannedData;

    public Calculator() {
        this.scan = new Scanner(System.in);
    }

//    public static void main(String[] args) {
//        Calculator calc = new Calculator();
//        calc.start();
//    }

    public void start() {
        RUNNING:
        while (true) {
            System.out.println("\t\t\t#List of Operators: ( + , - , * , / )");
            System.out.println("\t\t\t#You can write stop at any time to stop the program.");
            scannedData = scan.nextLine();
            operators = new ArrayList();

            splitted = scannedData.split(" ");

            int length = splitted.length - 1;

            for (int i = 0; i < splitted.length; i++) {
                if (splitted[i].trim().equalsIgnoreCase("stop")) {
                    System.out.println("\t\t\t#You have stopped the program. Thank you for using the calculator\n\t\tplease come again =)");
                    break RUNNING;
                }
            }
            if (splitted.length != 0) {

                OPERANTS:
                while (true) {
                    if (!isOperator(splitted[length])) {
                        for (int i = splitted.length - 1; isOperator(splitted[i]); i--) {
                            operators.add(splitted[i]);
                        }
                        break OPERANTS;
                    } else {
                        length--;
                    }
                }

                System.out.println("operators" + operators.size());

                NUMBER:
                while (true) {
                    if (!isNumber(splitted[length])) {
                        break NUMBER;
                    } else {
                        stack = new ArrayList();
                        for (int i = 0; i < splitted.length - length; i++) {
                            if (isNumber(splitted[i])) {
                                stack.add(Integer.parseInt(splitted[i]));
                            } else {
                                break NUMBER;
                            }
                        }
                        if (length == 0) {
                            break NUMBER;
                        } else {
                            length--;
                        }
                    }
                }

                if (operators.size() != 0 && stack.size() != 0) {

                    if (operators.size() >= stack.size()) {
                        System.out.println("\t\t\t#You have too many operants - please try again.\n");
                    } else if (operators.size() == 0) {
                        System.out.println("stack:");
                        for (int i = 0; i < stack.size(); i++) {
                            System.out.println(stack.get(i));
                        }
                    } else {

                        int result = 0;
                        while (operators.size() != 0) {

                            System.out.println("");
                            int num1 = stack.remove(stack.size() - 1);
                            int num2 = stack.remove(stack.size() - 1);
                            String operator = operators.remove(0);
                            result = calculateIt(num1, num2, operator);
                            stack.add(result);
                            System.out.println(" calculating: " + num1 + " " + operator + " " + num2 + " = " + result);
                            System.out.println("stack:");
                            for (int i = operators.size()-1; i >= 0 ; i--) {
                                System.out.println(operators.get(i));
                            }
                            for (int i = stack.size()-1; i >= 0 ; i--) {
                                System.out.println(stack.get(i));
                            }
                        }
                    }
                }
            }
        }
    }

    private int calculateIt(int num1, int num2, String operator) {
        switch (operator.trim()) {
            case "-":
                return num1 - num2;
            case "+":
                return num1 + num2;
            case "/":
                return Math.round(num1 / num2);
            case "*":
                return Math.round(num1 * num2);
            case "^":
                double result = Math.pow((double) num1, (double) num2);
                return (int) result;
            default:
                return 0;
        }
    }

    public static boolean isNumber(String s) {

        if (s.trim() == "0") {
            return true;
        }
        try {
            Integer.parseInt(s.trim());
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