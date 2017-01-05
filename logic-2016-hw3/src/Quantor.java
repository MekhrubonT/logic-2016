/**
 * Created by Lenovo on 05.01.2017.
 */
public class Quantor extends Expr {
    final String quan;
    final Var v;
    final Expr d;

    public Quantor(String quan, Var v, Expr d) {
        this.quan = quan;
        this.v = v;
        this.d = d;
    }

    @Override
    public Expr freeQuantor(Expr other) {
        return d.changeVarTo(v.toString(), other);
    }

    @Override
    public Expr changeVarTo(String var, Expr other) {
        if (var.equals(v.toString()))
            return this;
        return new Quantor(quan, v, d.changeVarTo(var, other));
    }

    @Override
    public String toString() {
        return quan + v.toString() + "(" + d.toString() + ")";
    }
}
