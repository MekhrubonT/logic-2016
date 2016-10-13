import org.omg.PortableServer.POA;

import java.util.ArrayList;

/**
 * Created by Lenovo on 13.10.2016.
 */
public class Parser {

    String expression;
    int pointer;
    BinaryOperation.Operation OPERATIONS[] =
            new BinaryOperation.Operation[]{BinaryOperation.Operation.CON,
                    BinaryOperation.Operation.OR, BinaryOperation.Operation.AND};

    Expression parse(String exp) {
        this.expression = exp;
        pointer = 0;
        return binaryOperations(0);
    }

    void skip() {
        while (pointer < expression.length() && Character.isWhitespace(expression.charAt(pointer))) {
            pointer++;
        }
    }

    boolean currentOperationIs(char c) {
        if (expression.charAt(pointer) == '-')
            pointer++;
        return expression.charAt(pointer) == c;
    }

    Expression binaryOperations(int pos) {
        if (pos == OPERATIONS.length) {
            return unaryOperations();
        }
        ArrayList<Expression> list = new ArrayList<Expression>() {{
            add(binaryOperations(pos + 1));
        }};
        while (pointer < expression.length()) {
            skip();
            if (pointer == expression.length() || !currentOperationIs(OPERATIONS[pos].toChar())) {
                break;
            }
            pointer++;
            list.add(binaryOperations(pos + 1));
        }
        Expression result = list.get(list.size() - 1);
        for (int i = list.size() - 2; i >= 0; i--) {
            result = new BinaryOperation(list.get(i), result, OPERATIONS[pos]);
        }
        return result;
    }
    Expression unaryOperations() {
        skip();
        switch (expression.charAt(pointer)) {
            case '(':
                pointer++;
                Expression result = binaryOperations(0);
                skip();
                pointer++;
                return result;
            case '!':
                pointer++;
                return new Negate(unaryOperations());
            default:
                int spointer = pointer;
                while (pointer < expression.length() && Character.isLetterOrDigit(expression.charAt(pointer))) {
                    pointer++;
                }
                return new Variable(expression.substring(spointer, pointer));
        }
    }

}
