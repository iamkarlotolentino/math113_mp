package team.bitwin.algo.newton;


public class Monomial extends Function {
    double coefficient;
    double constant;
    final char variable = 'x';
    double exponent;
    String whichConstructor;

    public Monomial() {}

    // Form: coefficient and variable "ax". 'a' can be equal to 1.
    public Monomial(double coefficient, char variable, String whichConstructor) {
        this.coefficient = coefficient;
        variable = 'x';
        this.whichConstructor = "ax";
    }

    // Form: coefficient, variable, and exponent "ax^n". 'a' can be equal to 1.
    public Monomial(double coefficient, char variable, double exponent, String whichConstructor) {
        this.coefficient = coefficient;
        variable = 'x';
        this.exponent = exponent;
        this.whichConstructor = "ax^n";
    }

    // Form: constant "c".
    public Monomial(double constant, String whichConstructor) {
        this.constant = constant;
        this.whichConstructor = "c";
    }

    public double getCoefficient() {
        return coefficient;
    }

    public double getExponent() {
        return exponent;
    }

    public double getConstant() {
        return constant;
    }

    public String getWhichConstructor() {
        return whichConstructor;
    }


    @Override
    /**
     * Runs all of the other methods in this class.
     * @param str, the input
     * @return derivative, the calculated output.
     */

    public double computeDerivative(String str, double xValue) {
        Double derivative = null;
        Monomial function = extractArgument(str);

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        derivative = calculator(function, xValue);
        return derivative;
    }


    @Override
    /**
     * Takes the input String and turns it into one of the forms of a Monomial.
     * @param input, an input string.
     * @return monomial, the formatted argument that the inputted String represents.
     */

    public Monomial extractArgument(String str) {
        // Removes every space in the String input and removes all capitals.
        str = str.trim().toLowerCase();
        for (int index = 0; index < str.length(); index++) {
            if (str.charAt(index) == ' ') {
                str = str.substring(0, index) + str.substring(index + 1);
            }
        }

        /*
         * Sets whichConstructor to the String result of determineConstructor(str);
         * Catches three possible exceptions: EmptyInputException, InvalidInputException, TryAgain.
         */

        String whichConstructor = "";

        try {
            whichConstructor = determineConstructor(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Switch statement to create the Monomial depending on whichConstructor.
         */

        double coefficient;
        double exponent;
        double constant;

        Monomial monomial = null;
        char sigChar = str.charAt(0);
        for (int index = 0; index < str.length(); index++) {
            if (str.charAt(index) == '+' || str.charAt(index) == '-') {
                str = str.substring(0, index) + str.substring(index + 1);
            }
        }
        System.out.println(str);
        switch (whichConstructor) {
            case ("c"):
                constant = Double.parseDouble(str);
                monomial = new Monomial(constant, whichConstructor);
                break;
            case ("ax"):
                if (str.length() == 1) {
                    if (sigChar == '-') {
                        coefficient = -1;
                    } else {
                        coefficient = 1;
                    }
                } else {
                    if (sigChar == '-') {
                        coefficient = 0 - Double.parseDouble(str.substring(0, str.indexOf('x')));
                    } else {
                        coefficient = Double.parseDouble(str.substring(0, str.indexOf('x')));
                    }
                }
                monomial = new Monomial(coefficient, 'x', whichConstructor);
                break;
            case ("ax^n"):
                if (str.indexOf('x') == 0) {
                    if (sigChar == '-') {
                        coefficient = -1;
                    } else {
                        coefficient = 1;
                    }
                } else {
                    if (sigChar == '-') {
                        coefficient = 0 - Double.parseDouble(str.substring(0, str.indexOf('x')));
                    } else {
                        coefficient = Double.parseDouble(str.substring(0, str.indexOf('x')));
                    }
                }
                exponent = Double.parseDouble(str.substring(str.indexOf('^') + 1));
                monomial = new Monomial(coefficient, 'x', exponent, whichConstructor);
                break;
        }
        return monomial;
    }


    @Override
    /**
     * Determines which constructor the input function belongs to.
     * @param str, the input string.
     * @return whichConstructor, the constructor the input follows.
     * @throw EmptyInputException, InvalidInputException, ErrorOccurredException.
     */

    public String determineConstructor(String str) throws Exception {

        /*
         * Determines if the input is empty.
         * If the input is empty, it throws an EmptyInputException.
         * Else, it moves on to the other statements in this method.
         */

        if (str.length() == 0) {
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

        /*
         * Determines if it's a constant by seeing if there's an 'x' in the equation.
         * If there is no 'x', it is a constant and it returns "c".
         * Else it moves on to the other statements.
         */

        if (!str.contains("x")) {
            return "c";
        }

        /*
         * Determines if it's in form "ax^n" with a coefficient, variable, and an exponent, or in form "ax" with a coefficient and a variable
         * by seeing if there's a '^' and an 'x' in the equation.
         * If there is an 'x' and a '^' in the String, it returns "ax^n" (if there is no 'a', then 'a' will later be considered to be 1.)
         * If there is an 'x' but no '^' in the String, it returns "ax" (if there is no 'a', then 'a' will later be considered to be 1.)
         * Else it throws an InvalidInputException.
         */

        if (str.contains("x")) {
            if (str.contains("^")) {
                return "ax^n";
            }
            return "ax";
        } else {
            throw ErrorOccurredException;
        }
    }


    @Override
    /**
     * Determines if the Monomial is differentiable.
     * Even though for most cases it will be differentiable, this process is just a final checker.
     * @param function, the input function.
     * @param xValue, the input X value.
     * @return differentiable, the boolean on whether the function is differentiable or not.
     * @throws NoYValueException, NotDiffrentiableException, TooLargeNumberException.
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
     * Finds the Y value given a Monomial and an X value.
     * @param function, the input function.
     * @param xValue, the input X value.
     * @return y, the found Y value.
     */

    public Double findY(Function function, double xValue) {

        Double y = null;
        Monomial monomial = (Monomial) function;

        switch (monomial.getWhichConstructor()) {
            case ("c"):
                y = monomial.getConstant();
                break;
            case ("ax"):
                y = monomial.getCoefficient() * xValue;
                break;
            case ("ax^n"):
                y = monomial.getCoefficient() * (Math.pow(xValue, monomial.getExponent()));
                break;
        }
        return y;
    }


    @Override
    /**
     * Determines the left limit of the function using the function and the Y value.
     * The method is geometric: (f(x) - f(x - 0.00001))/0.00001.
     * @param function, the input function.
     * @param xValue, the input X value.
     * @return leftLimit, the found limit value, rounded to 5 places.
     */

    public Double leftLimit(Function function, double xValue) {

        Double leftLimit = null;
        Monomial monomial = (Monomial) function;

        switch (monomial.getWhichConstructor()) {
            case ("c"):
                leftLimit = (monomial.getConstant() - monomial.getConstant()) / 0.0000000001;
                break;
            case ("ax"):
                leftLimit = ((monomial.getCoefficient() * xValue) - (monomial.getCoefficient() * (xValue - 0.0000000001))) / 0.0000000001;
                break;
            case ("ax^n"):
                leftLimit = (monomial.getCoefficient() * Math.pow(xValue, monomial.getExponent()) - (monomial.getCoefficient() * Math.pow(xValue - 0.00001, monomial.getExponent()))) / 0.0000000001;
                break;
        }
        leftLimit = Double.parseDouble(String.format("%.5g%n", leftLimit));
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

        Double rightLimit = null;
        Monomial monomial = (Monomial) function;

        switch (monomial.getWhichConstructor()) {
            case ("c"):
                rightLimit = (monomial.getConstant() - monomial.getConstant()) / 0.0000000001;
                break;
            case ("ax"):
                rightLimit = ((monomial.getCoefficient() * (xValue + 0.0000000001)) - (monomial.getCoefficient() * xValue)) / 0.0000000001;
                break;
            case ("ax^n"):
                rightLimit = (monomial.getCoefficient() * Math.pow(xValue + 0.0000000001, monomial.getExponent()) - (monomial.getCoefficient() * Math.pow(xValue, monomial.getExponent()))) / 0.0000000001;
                break;
        }
        rightLimit = Double.parseDouble(String.format("%.5g%n", rightLimit));
        return rightLimit;
    }


    @Override
    /**
     * Method to algebraically calculate the derivative of an inputted Monomial by using various algebraic rules.
     * @param function, the inputted Monomial.
     * @param xValue, the inputted X value.
     * @return derivative, the calculated derivative of the Monomial at xValue.
     */

    public Double calculator(Function function, double xValue) {

        Double derivative = null;
        Monomial monomial = (Monomial) function;

        switch (monomial.getWhichConstructor()) {
            case ("c"):
                derivative = 0.0;
                break;
            case ("ax"):
                derivative = monomial.getCoefficient();
                break;
            case ("ax^n"):
                derivative = ((monomial.getExponent() * monomial.getCoefficient() * Math.pow(xValue, monomial.getExponent() - 1)));
        }

        return derivative;
    }

}
