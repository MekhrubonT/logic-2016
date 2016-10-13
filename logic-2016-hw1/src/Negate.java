import java.util.HashMap;

public class Negate extends Expression {
    final int NEGATE = 1;
    Expression neg;

    Negate(Expression neg) {
        this.neg = neg;
        instance = NEGATE;
        cachedHashCode = '!' + neg.hashCode() * HASHP;
    }

    public String toString() {
        return "!" + neg.toString();
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d, boolean type) {
        return !(o == null || o.instance != instance) && neg.equalStruct(((Negate) o).neg, d, type);
    }
}
