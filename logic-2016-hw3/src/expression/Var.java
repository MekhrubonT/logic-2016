package expression;

/**
 * Created by Lenovo on 05.01.2017.
 */
public class Var extends Expr {
    final String var;

    public Var(String s) {
        this.var = s;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public Expr changeVarTo(String var, Expr other) {
        if (this.var.equals(var))
            return other;
        return this;
    }
}
