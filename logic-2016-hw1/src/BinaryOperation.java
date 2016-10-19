import java.util.HashMap;

public class BinaryOperation extends Expression {
    static final int BINARYOPERATION = 0;
    Expression lhs, rhs;
    Operation op;

    BinaryOperation(Expression l, Expression r, Operation d) {
        lhs = l;
        rhs = r;
        op = d;
        instance = BINARYOPERATION;

        cachedToString = lhs.cachedToString + op + "(" + rhs.cachedToString + ")";
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        if (o == null || o.instance != instance) {
            return false;
        }
        BinaryOperation other = (BinaryOperation) o;
        return op == other.op && lhs.equalStruct(other.lhs, d) && rhs.equalStruct(other.rhs, d);
    }


    enum Operation {
        AND, OR, CON;

        char toChar() {
            switch (this) {
                case AND:
                    return '&';
                case OR:
                    return '|';
                default:
                    return '>';
            }
        }
    }
}
