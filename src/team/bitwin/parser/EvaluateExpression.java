package team.bitwin.parser;

import team.bitwin.model.TokenQueue;

import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static team.bitwin.parser.ValidatorUtils.isNumber;

public class EvaluateExpression {
    private static final Map<String, String> constantValues = new HashMap<>();
    private final Parser parser = new Parser();
    DecimalFormat df = new DecimalFormat("0.0000000000");

    public EvaluateExpression() {
        constantValues.put("E", df.format(Math.E));
        constantValues.put("PI", df.format(Math.PI));
    }

    public double evaluateExpression(String input, double x) {
        constantValues.put("x", String.valueOf(df.format(x)));
        constantValues.put("-x", x < 0 ? df.format(x*-1) : "-" + df.format(x));
        TokenQueue tokens = parser.parseString(input, constantValues);
        System.out.print(input + " : " + x);
        System.out.println("::--" +tokens);
        tokens = parser.infixToPostfix(tokens);
        return evaluatePostfix(tokens);
    }

    public double evaluatePostfix(TokenQueue postfix) {
        Stack<String> finalValue = new Stack<>();
        double rightHand, leftHand;

        while (!postfix.isEmpty()) {
            String token = postfix.removeLast();

            if (isNumber(token)) {
                finalValue.push(token);
            } else {
                // Token is operator..
                try {
                    switch (token) {
                        case "+":
                            rightHand = Double.parseDouble(finalValue.pop());
                            leftHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(leftHand + rightHand));
                            break;
                        case "-":
                            rightHand = Double.parseDouble(finalValue.pop());
                            leftHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(leftHand - rightHand));
                            break;
                        case "/":
                            rightHand = Double.parseDouble(finalValue.pop());
                            leftHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(leftHand / rightHand));
                            break;
                        case "*":
                            rightHand = Double.parseDouble(finalValue.pop());
                            leftHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(leftHand * rightHand));
                            break;
                        case "TAN":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.tan(rightHand)));
                            break;
                        case "COS":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.cos(rightHand)));
                            break;
                        case "SIN":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.sin(rightHand)));
                            break;
                        case "SQRT":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.sqrt(rightHand)));
                            break;
                        case "^":
                            rightHand = Double.parseDouble(finalValue.pop());
                            leftHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.pow(leftHand, rightHand)));
                            break;
                        case "CBRT":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.cbrt(rightHand)));
                            break;
                        case "LOG":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.log10(rightHand)));
                            break;
                        case "TANH":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.tanh(rightHand)));
                            break;
                        case "COSH":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.cosh(rightHand)));
                            break;
                        case "SINH":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.sinh(rightHand)));
                            break;
                        case "ATAN":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.atan(rightHand)));
                            break;
                        case "ACOS":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.acos(rightHand)));
                            break;
                        case "ASIN":
                            rightHand = Double.parseDouble(finalValue.pop());
                            finalValue.push(String.valueOf(Math.asin(rightHand)));
                            break;
                        default:
                            // Operator token is unidentifiable
                            System.err.println("Unknown operator: " + token);
                            throw new IllegalArgumentException("Invalid f(x). Unknown operator is found");
                    }
                } catch (EmptyStackException ese) {
                    // Mismatched number of operators
                    throw new IllegalArgumentException("Invalid (x). Verify your operations");
                }
            }
        }

        // The list should contain one item as the result only
        if (finalValue.size() > 1) {
            System.err.println("Expression stack: " + finalValue);
            throw new IllegalArgumentException("Invalid f(x). An incorrect operation is detected");
        }

        return Double.parseDouble(finalValue.pop());
    }
}
