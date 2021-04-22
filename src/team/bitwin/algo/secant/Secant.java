package team.bitwin.algo.secant;

import team.bitwin.parser.EvaluateExpression;

import java.util.Stack;

public class Secant {
    private final EvaluateExpression evaluator = new EvaluateExpression();

    public Stack<String[]> approximateRoot(double x0, double x1, double e, String f, Stack<String[]> prev) {
        double fOfX0 = evaluator.evaluateExpression(f, x0);
        double fOfX1 = evaluator.evaluateExpression(f, x1);
        double nextX = x1 - fOfX1 * ((x1 - x0) / (fOfX1 - fOfX0));

        if (prev.size() == 0) {
            String[] iterationResult = {
                    String.valueOf(x0),
                    String.valueOf(fOfX0),
                    " "
            };
            prev.push(iterationResult);
            String[] iterationResult2 = {
                    String.valueOf(x1),
                    String.valueOf(fOfX1),
                    String.valueOf(x1 - x0)
            };
            prev.push(iterationResult2);

            return approximateRoot(x1, nextX, e, f, prev);
        }

        String[] iterationResult = {
                String.valueOf(x1),
                String.valueOf(fOfX1),
                String.valueOf(x1 - x0)
        };
        prev.push(iterationResult);

        if (!(Math.abs(x1 - x0) <= e)) {
            return approximateRoot(x1, nextX, e, f, prev);
        }

        return prev;
    }
}
