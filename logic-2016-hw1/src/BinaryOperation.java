import java.util.HashMap;

public class BinaryOperation extends Expression {
    static final int BINARYOPERATION = 0;
    Expression lhs, rhs;
    Operation op;
    int cachedHashCode;

    BinaryOperation(Expression l, Expression r, Operation d) {
        lhs = l;
        rhs = r;
        op = d;
        instance = BINARYOPERATION;
        cachedHashCode = (lhs.hashCode() * HASHP + op.toChar()) * HASHP100 + rhs.hashCode();
    }

    public String toString() {
        return "(" + lhs.toString() + ")" + op.toChar() + "(" + rhs.toString() + ")";
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d, boolean type) {
        if (o == null || o.instance != instance) {
            return false;
        }
        BinaryOperation other = (BinaryOperation) o;
        return op == other.op && lhs.equalStruct(other.lhs, d, type) && rhs.equalStruct(other.rhs, d, type);
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
