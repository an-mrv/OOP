import java.util.Stack;

/**
 * A class for calculating expressions in prefix form.
 */

public class Expression {
    String[] expr;
    Stack<Double> stack;

    /**
     * Constructor.
     *
     * @param expr expression as a string
     */
    public Expression(String expr) {
        this.expr = expr.split(" ");
        this.stack = new Stack<>();
    }

    /**
     * The method for calculating.
     *
     * @return the calculated real number
     * @throws WrongArgumentException an exception is made if one of the arguments of the
     * expression is written incorrectly
     * @throws WrongPrefixNotationException an exception is made if there is an error in the prefix notation
     */
    public double calculate() throws
            WrongArgumentException, WrongPrefixNotationException {
        for (int i = this.expr.length - 1; i >= 0; i--) {
            if (this.expr[i].matches("^[0-9]+[.]?[0-9]*$")) {
                this.stack.push(Double.parseDouble(expr[i]));
            }
            else {
                this.stack.push(operation(expr[i]));
            }
        }
        if (this.stack.size() != 1) {
            throw new WrongPrefixNotationException("Wrong expression!");
        }
        return stack.pop();
    }

    /**
     * A method for extracting two arguments from a stack.
     *
     * @return an array of two real numbers
     */
    private Double[] pop2Args() throws WrongPrefixNotationException{
        Double[] res = new Double[2];
        if (stack.size() < 2)
            throw new WrongPrefixNotationException("Wrong expression!");
        res[0] = stack.pop();
        res[1] = stack.pop();
        return res;
    }

    /**
     * A method for extracting one argument from a stack.
     *
     * @return a real number
     */
    private Double pop1Arg() throws WrongPrefixNotationException{
        if (stack.empty())
            throw new WrongPrefixNotationException("Wrong expression!");
        return stack.pop();
    }

    /**
     * A method that calculates a single operation from an expression
     *
     * @param operator one of the operators +, -, *, /, log, pow, sqrt, sin, cos
     * (In the operator log: the first argument is the base of the logarithm, the second - the number)
     * @return the calculated real number
     */
    private Double operation(String operator) throws
            WrongPrefixNotationException, WrongArgumentException {
        Double[] args;
        switch (operator) {
            case "+":
                args = pop2Args();
                return args[0] + args[1];
            case "-":
                if (this.stack.size() == 1) {
                    return pop1Arg() * (-1);
                } else {
                    args = pop2Args();
                    return args[0] - args[1];
                }
            case "*":
                args = pop2Args();
                return args[0] * args[1];
            case "/":
                args = pop2Args();
                return args[0] / args[1];
            case "log":
                args = pop2Args();
                return Math.log(args[1]) / Math.log(args[0]);
            case "pow":
                args = pop2Args();
                return Math.pow(args[0], args[1]);
            case "sin":
                return Math.sin(pop1Arg());
            case "cos":
                return Math.cos(pop1Arg());
            case "sqrt":
                return Math.sqrt(pop1Arg());
            default:
                throw new WrongArgumentException("Wrong operator!");
        }
    }
}
