import java.util.HashMap;
import java.util.Map;

public class BinaryOperation extends Expression {
//    static final int BINARYOPERATION = 0;
    Expression lhs, rhs;
    Operation op;

    BinaryOperation(Expression l, Expression r, Operation d) {
        lhs = l;
        rhs = r;
        op = d;
        instance = d.value;

        children.add(lhs);
        children.add(rhs);

        cachedToString = "  (" + lhs.cachedToString + op + rhs.cachedToString + ")  ";
    }



    public enum Operation {
        AND('&'), OR('|'), CON('>'), PLUS('+'), MULT('*'), EQV('=');

        char value;
        Operation(char value) {
            this.value = value;
        }
        char toChar() {
            return value;
        }

        @Override
        public String toString() {
            if (value == '>')
                return "->";
            return String.valueOf(value);
        }
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        if (o == null || o.instance != instance) {
            return false;
        }

        BinaryOperation other = (BinaryOperation) o;
        return lhs.equalStruct(other.lhs, d) && rhs.equalStruct(other.rhs, d);
    }

}
