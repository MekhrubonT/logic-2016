package expression;

/**
 * Created by Lenovo on 05.01.2017.
 */
public class Negate extends Expr {
    final Expr d;

    public Negate(Expr d) {
        this.d = d;
    }

    @Override
    public Expr changeVarTo(String var, Expr other) {
        return new Negate(d.changeVarTo(var, other));
    }

    @Override
    public String toString() {
        return "!" + d.toString();
    }
}
