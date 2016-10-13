import java.util.HashMap;

/**
 * Created by Lenovo on 13.10.2016.
 */



public class BinaryOperation extends Expression {
    enum Operation {
        AND, OR, CON;
        char toChar() {
            switch (this) {
                case AND: return '&';
                case OR: return '|';
                default: return '>';
            }
        }
    }
    Expression lhs, rhs;
    Operation op;
    static final int BINARYOPERATION = 0;
    BinaryOperation (Expression l, Expression r, Operation d) {
        lhs = l;
        rhs = r;
        op = d;
        instance = BINARYOPERATION;
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
}
