package team.bitwin.algo.newton;

public abstract class Function {

    abstract double computeDerivative(String str, double xValue) throws Exception;

    abstract Function extractArgument(String str);

    abstract String determineConstructor(String str) throws Exception;

    abstract boolean seeIfDifferentiable(Function function, double xValue) throws Exception;

    abstract Double findY(Function function, double xValue);

    abstract Double leftLimit(Function function, double xValue);

    abstract Double rightLimit(Function function, double xValue);

    abstract Double calculator(Function function, double xValue);

    public static final Exception EmptyInputException = new Exception("Your input was empty. Please try again.");
    public static final Exception InvalidInputException = new Exception("Your input was invalid. Please try again.");
    public static final Exception NoYValueException = new Exception("This function is not differentiable. The inputted X value doesn't have a corresponding Y value. Please try again.");
    public static final Exception NotDiffrentiableException = new Exception("This function is not differentiable. The left limit doesn't equal the right limit. Please try again.");
    public static final Exception ErrorOccurredException = new Exception("An error occurred. Please try again.");
    public static final Exception TooLargeNumberException = new Exception("The numbers you inputted were too large. Please try again.");
    public static final Exception InvalidXValueException = new Exception("Your inputted X value was invalid. Please try again.");

}
