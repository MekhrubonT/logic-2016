import java.util.HashMap;
import java.util.Random;

public class BinaryOperation extends Expression {
    //    static final int BINARYOPERATION = 0;
//    Expression lhs, rhs;
    Operation op;

    BinaryOperation(Expression l, Expression r, Operation d) {
//        lhs = l;
//        rhs = r;
        op = d;
        instance = d.value;

        children.add(l);
        children.add(r);

        Random rand = new Random();
        if (op == Operation.CON && rand.nextInt() % 100 <= 80)
            cachedToString = "(" + l.toString() + op + r.toString() + ")";
        else
            cachedToString = null;
    }

    @Override
    public String toString() {
        if (cachedToString == null)
            return "(" + children.get(0).toString() + op + children.get(1).toString() + ")";
        return cachedToString;
    }

    public boolean equalStruct(Expression other, HashMap<String, String> d) {
        if (other == null || other.instance != instance) {
            return false;
        }

        return children.get(0).equalStruct(other.children.get(0), d)
                && children.get(1).equalStruct(other.children.get(1), d);
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

}
