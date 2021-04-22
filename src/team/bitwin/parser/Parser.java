package team.bitwin.parser;

import team.bitwin.model.TokenQueue;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static team.bitwin.parser.ValidatorUtils.isAlpha;
import static team.bitwin.parser.ValidatorUtils.isNumber;

public class Parser {

    public TokenQueue parseString(String s, Map<String, String> substitution) {
        Pattern pattern = Pattern.compile("\\.?([0-9]+|(?<=[+|\\-*/])[-][0-9]+|(?<![0-9a-z])[-][0-9]+)(\\.[0-9]+)?|[(|)]|[A-Z]+|[a-z]|(?<=[+|\\-*/])[-][x]+|(?<![0-9a-z])[-][x]|[+|\\-/*^]");
        Matcher matcher = pattern.matcher(s.replaceAll("\\s+", ""));
        TokenQueue tokens = new TokenQueue();

        // Validate the parentheses count
        int parenCount = 0;
        // Validate string length
        int strLength = 0;

        String previousToken = "";
        while (matcher.find()) {
            String token = matcher.group();

            // On special case where a number is followed by a variable
            // Then, we place a multiplication in between
            if (isNumber(previousToken) && isAlpha(token)) {
                tokens.add("*");
            }

            // Counting the parentheses for validation
            if (token.equals("(")) parenCount++;
            if (token.equals(")")) parenCount--;

            // Counting matcher string length
            strLength += token.length();

            // On special case where a variable has an equivalent value
            // Then it is replaced, otherwise the token itself is pushed
            tokens.add(substitution.getOrDefault(token, token));
            previousToken = token;
        }

        // Verify if the overall parsed token's length is equivalent to the original string length
        if (strLength != s.length())
            throw new IllegalArgumentException("Invalid f(x). An invalid character is found. Double-check your input");

        // Verify if the parentheses have pairs accordingly
        if (parenCount != 0)
            throw new IllegalArgumentException("Invalid f(x) input. Double-check the parenthesis count");

        return tokens;
    }

    public TokenQueue infixToPostfix(TokenQueue tokens) {
        TokenQueue postfix = new TokenQueue();
        TokenQueue operators = new TokenQueue();

        for (String token : tokens) {
            // Is operand?
            if (getPrecedence(token) > 0) {
                while (!operators.isEmpty() && getPrecedence(operators.peek()) >= getPrecedence(token)) {
                    postfix.push(operators.pop());
                }
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    postfix.push(operators.pop());
                }
                // Remove the remaining "("
                operators.pop();
            } else if (token.equals("(")) {
                operators.push(token);
            } else {
                // Is value?
                postfix.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfix.push(operators.pop());
        }

        return postfix;
    }

    public byte getPrecedence(String token) {
        switch (token) {
            case "+":
            case "-":
                return 1;
            case "/":
            case "*":
                return 2;
            case "^":
            case "TAN":
            case "TANH":
            case "ATAN":
            case "COS":
            case "COSH":
            case "ACOS":
            case "SIN":
            case "SINH":
            case "ASIN":
            case "SQRT":
            case "CBRT":
            case "LOG":
                return 3;
        }
        return -1;
    }
}
