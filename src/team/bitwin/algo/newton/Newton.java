package team.bitwin.algo.newton;

import org.matheclipse.core.eval.ExprEvaluator;
import team.bitwin.parser.EvaluateExpression;

import java.text.DecimalFormat;
import java.util.Stack;

public class Newton {
    private final EvaluateExpression evaluator = new EvaluateExpression();
    ExprEvaluator util = new ExprEvaluator();

    public Stack<String[]> approximateRoot(double x, double errorTolerance, String f, Stack<String[]> prev) throws Exception {
        DecimalFormat format = new DecimalFormat("0.0000000000");

        double fOfX;
        double fpOfX = 0;
        String derivF = util.eval("diff(" + f +",x)").toString();
        String errorEst = " ";
        fOfX = evaluator.evaluateExpression(f, x);

        if (prev.size() != 0) {
            errorEst = String.valueOf(x - Double.parseDouble(prev.lastElement()[0]));
            String[] iterationResult = {
                    String.valueOf(x),
                    String.valueOf(fOfX),
                    errorEst
            };
            prev.push(iterationResult);
            fpOfX = evaluator.evaluateExpression(derivF, x);
            if (!(Math.abs(x - Double.parseDouble(prev.get(prev.indexOf(prev.lastElement()) - 1)[0])) <= errorTolerance)) {
                double nextX = x - (fOfX / fpOfX);
                return approximateRoot(nextX, errorTolerance, f, prev);
            }
        } else {

            String[] iterationResult = {
                    String.valueOf(x),
                    String.valueOf(fOfX),
                    errorEst
            };
            prev.push(iterationResult);

            fpOfX = evaluator.evaluateExpression(derivF, x);

            double nextX = x - (fOfX / fpOfX);

            return approximateRoot(nextX, errorTolerance, f, prev);

        }

        return prev;
    }

    public String findType(String input) throws Exception {
        String type = "";

        /*
         * Determines if the input is an empty String.
         * If it is an empty String, it throws an EmptyInputException.
         * Else, it moves on the other statements in this method.
         *
         */

        if (input.length() == 0) {
            throw Function.EmptyInputException;
        }

        // Removes every space in the String input and removes all capitals.

        input = input.trim().toLowerCase();
        for (int index = 0; index < input.length(); index++) {
            if (input.charAt(index) == ' ') {
                input = input.substring(0, index) + input.substring(index + 1);
            }
        }

        // Determines if the function is a Polynomial or a Monomial by finding the position of '+' or '-'.

        if (input.indexOf('+') != -1 || ((input.indexOf('-') != -1 && input.indexOf('x') != -1) && input.indexOf('-') > input.indexOf('x'))) {
            type = "Polynomial";
        } else {
            type = "Monomial";
        }

        /*
         * Broaden parameters as needed for expansion of this program.
         */
        return type;

    }
}
