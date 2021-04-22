package team.bitwin.algo.bisection;

import team.bitwin.parser.EvaluateExpression;

import java.text.DecimalFormat;
import java.util.Stack;

public class Bisection {

    private EvaluateExpression evaluator = new EvaluateExpression();

    public boolean isValidInterval(double a, double b, String f) {
        return (evaluator.evaluateExpression(f, a) <= 0 && evaluator.evaluateExpression(f, b) >= 0)
                || (evaluator.evaluateExpression(f, b) <= 0 && evaluator.evaluateExpression(f, a) >= 0);
    }

    public Stack<String[]> approximateRoot(double a, double b, double e, String f, Stack<String[]> prev) {
        double c = (a + b) / 2;

        DecimalFormat format = new DecimalFormat("0.0000000000");
        String[] iterationResult = {
                format.format(a),
                format.format(b),
                format.format(c),
                format.format(b - c),
                format.format(evaluator.evaluateExpression(f, c))
        };
        prev.push(iterationResult);

        if (!(Math.abs(b - c) <= e)) {
            double fOfb = evaluator.evaluateExpression(f, b);
            double fOfc = evaluator.evaluateExpression(f, c);

            if (fOfb * fOfc <= 0) {
                return approximateRoot(c, b, e, f, prev);
            } else {
                return approximateRoot(a, c, e, f, prev);
            }
        }
        return prev;
    }
}
