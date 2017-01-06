import java.util.HashMap;

public class Negate extends Expression {
    final static int NEGATE = 1;
    Expression neg;

    Negate(Expression neg) {
        this.neg = neg;
        instance = NEGATE;

        children.add(neg);

        cachedToString = "(!" + neg.toString() + ")";
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        return !(o == null || o.instance != instance) && neg.equalStruct(((Negate) o).neg, d);
    }
}
