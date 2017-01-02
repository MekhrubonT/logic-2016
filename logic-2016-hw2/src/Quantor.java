import java.util.HashMap;

/**
 * Created by Lenovo on 26.12.2016.
 */
public class Quantor extends Expression {
//    final static int QUANTOR = 5;
    final char q;
    final Variable var;
    final Expression cur;

    public Quantor(char q, Variable var, Expression cur) {
        this.q = q;
        this.var = var;
        this.cur = cur;
        cachedToString = "(" + q + var.toString() + cur.toString() + ")";
        instance = q;

        children.add(var);
        children.add(cur);
    }

    @Override
    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        throw new RuntimeException("Quantor.equalstruct");
//        return false;
    }
}
