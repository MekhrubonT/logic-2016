package expression;

/**
 * Created by Lenovo on 05.01.2017.
 */
public abstract class Expr {
    public Expr freeQuantor(Expr other) {
        return this;
    }
    public abstract Expr changeVarTo(String var, Expr other);
}
