import java.util.HashMap;

/**
 * Created by Lenovo on 13.10.2016.
 */
public class Negate extends Expression {
    Expression neg;
    final int NEGATE = 1;
    Negate(Expression neg) {
        this.neg = neg;
        instance = NEGATE;
    }

    public String toString() {
        return "!" + neg.toString();
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d, boolean type) {
        if (o == null || o.instance != instance) {
            return false;
        }
        return neg.equalStruct(((Negate) o).neg, d, type);
    }
}
