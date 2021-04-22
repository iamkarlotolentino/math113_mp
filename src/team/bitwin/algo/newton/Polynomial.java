package team.bitwin.algo.newton;

import java.util.ArrayList;

public class Polynomial extends Function {
    ArrayList<Monomial> arrayList = new ArrayList<>();

    public Polynomial() { }

    //Form: "ArrayList". No variable "whichConstructor" needed.
    public Polynomial(ArrayList<Monomial> arrayList) {
        this.arrayList = arrayList;
    }

    // Getter method.
    public ArrayList<Monomial> getArrayList() {
        return this.arrayList;
    }


    @Override
    /**
     * Runs all of the other methods in this class.
     * @param str, the input
     * @return derivative, the calculated output.
     */

    public double computeDerivative(String str, double xValue) throws Exception {

        Double derivative = null;
        Polynomial function = extractArgument(str);

        try {
            seeIfDifferentiable(function, xValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        derivative = calculator(function, xValue);

        return derivative;
    }


    @Override
    /**
     * Takes the input String and turns it into a Polynomial.
     * @param input, an input string.
     * @return polynomial, the formatted argument that the  String represents.
     */

    public Polynomial extractArgument(String str) {

        // Removes every space in the String input and removes all capitals.

        str = str.trim().toLowerCase();
        for (int index = 0; index < str.length(); index++) {
            if (str.charAt(index) == ' ') {
                str = str.substring(0, index) + str.substring(index + 1);
            }
        }

        // Sets whichConstructor to the String result of determineConstructor(str), which is always "ArrayList".

        try {
            determineConstructor(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Isolates each term in the Polynomial and makes them into Monomial objects.
         * It does this by taking the '+' or '-' signs and transforming everything before them into a Monomial.
         * Then, it gets rid of the operator symbol and continues with the rest of the String.
         */

        Monomial term = new Monomial();
        ArrayList<Monomial> arrayList = new ArrayList<Monomial>();
        Polynomial polynomial = new Polynomial(arrayList);

        /*
         * The final int strLength means that this nested for loop will iterate extra times.
         * This is necessary so that the loop doesn't stop short of the full expression and will instead calculate the derivative for all terms.
         */
        final int strLength = str.length();
        char previousOp = '+';
        if (str.charAt(0) == '-') {
            previousOp = '-';
        }

        for (int i = 0; i <= strLength + 1; i++) {
            for (int index = 1; index < str.length(); index++) {
                if (str.charAt(index) == '+') {
                    term = term.extractArgument(previousOp + str.substring(0, index));
                    polynomial.arrayList.add(term);
                    str = str.substring(index + 1);
                    previousOp = '+';
                    break;
                } else if (str.charAt(index) == '-') {
                    term = term.extractArgument(previousOp + str.substring(0, index));
                    polynomial.arrayList.add(term);
                    str = str.substring(index + 1);
                    previousOp = '-';
                    break;
                }
            }
        }
        return polynomial;
    }


    @Override
    /**
     * Determines if there's an error with the input String. If there is no error, it returns "ArrayList".
     * @param str, the input string.
     * @return whichConstructor, the constructor the input follows.
     * @throw EmptyInputException, InvalidInputException.
     */

    public String determineConstructor(String str) throws Exception {

        /*
         * Determines if the input is empty.
         * If the input is empty, it throws an EmptyInputException.
         * Else, it moves on to the other statements in this method.
         */

        if (str.length() == 0 || str == null) {
            throw EmptyInputException;
        }

        /*
         * Filters out extraneous characters by seeing if there's anything but 'x', '^', or numbers in the String.
         * If it finds extraneous characters, it throws an InvalidInputException.
         * Else, it moves on to the other statements in this method.
         */

        for (int index = 0; index < str.length(); index++) {
            if (!(str.charAt(index) == 'x' && str.charAt(index) == 'X') && str.charAt(index) == '^' && str.charAt(index) == '.' &&
                    (Character.isDigit((Character) str.charAt(index)))) {
                throw InvalidInputException;
            }
        }
        return "ArrayList";
    }


    /**
     * Determines if the Polynomial is differentiable.
     * Even though for most cases it will be differentiable, this process is just a final checker.
     *
     * @param function, the input function.
     * @param xValue,   the input X value.
     * @return differentiable, the boolean on whether the function is differentiable or not.
     */

    public boolean seeIfDifferentiable(Function function, double xValue) throws Exception {
        boolean differentiable = false;

        // Throws exceptions if there is no corresponding Y value to the given X value
        if (findY(function, xValue) == null) {
            throw NoYValueException;
        }

        // Compares leftLimit and rightLimit. If they are equal, it is differentiable. If not, it throws a NotDiffrentiableException.
        if (leftLimit(function, xValue).equals(rightLimit(function, xValue))) {
            differentiable = true;
        } else {
            throw NotDiffrentiableException;
        }

        // If number is larger than 1E50, it throws a TooLargeNumberException.
        if (leftLimit(function, xValue) > Math.pow(10, 50) || rightLimit(function, xValue) > Math.pow(10, 50)) {
            throw TooLargeNumberException;
        }
        return differentiable;
    }


    @Override
    /**
     * Finds the Y value given a Polynomial and an X value.
     * @param function, the input function.
     * @param xValue, the input X value.
     * @return y, the found Y value.
     */

    public Double findY(Function function, double xValue) {
        Double y = null;
        Polynomial polynomial = (Polynomial) function;
        ArrayList<Monomial> arrayList = polynomial.getArrayList();

        if (arrayList.size() == 0) {
            return y;
        } else {
            y = 0.0;
            for (Monomial term : arrayList) {
                y += term.findY(term, xValue);
            }
            return y;
        }
    }


    @Override
    /**
     * Determines the left limit of the function using the function and the Y value.
     * The method is geometric: (f(x) - f(x - 0.0000000001))/0.0000000001.
     * @param function, the input function.
     * @param xValue, the input X value.
     * @return leftLimit, the found limit value, rounded to 5 places.
     */

    public Double leftLimit(Function function, double xValue) {
        double leftLimit = Double.parseDouble(String.format("%.5g%n", (findY(function, xValue) - findY(function, xValue - 0.0000000001) / 0.0000000001)));
        return leftLimit;
    }


    @Override
    /**
     * Determines the right limit of the function using the function and the Y value.
     * The method is geometric: (f(x + 0.0000000001) - f(x))/0.0000000001.
     * @param function, the input function.
     * @param xValue, the input X value.
     * @return rightLimit, the found limit value, rounded to 5 places..
     */

    public Double rightLimit(Function function, double xValue) {
        double rightLimit = Double.parseDouble(String.format("%.5g%n", (findY(function, xValue + 0.0000000001) - findY(function, xValue) / 0.0000000001)));
        return rightLimit;
    }

    @Override
    /**
     * Method to algebraically calculate the derivative of an  Polynomial by adding the derivatives of its terms.
     * @param function, the  Polynomial.
     * @param xValue, the  X value.
     * @return derivative, the calculated derivative of the Polynomial at xValue.
     */

    public Double calculator(Function function, double xValue) {
        Double derivative = null;
        Polynomial polynomial = (Polynomial) function;
        ArrayList<Monomial> arrayList = polynomial.getArrayList();

        if (arrayList.size() == 0) {
            return derivative;
        } else {
            derivative = 0.0;
            for (Monomial term : arrayList) {
                derivative += term.calculator(term, xValue);
            }
            return derivative;
        }
    }
}