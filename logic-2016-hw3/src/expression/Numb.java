package expression;

/**
 * Created by Lenovo on 05.01.2017.
 */
public class Numb extends Expr {
    public final int k;
    final Expr d;

    public Numb(int k, Expr d) {
        this.k = k;
        this.d = d;
    }

    @Override
    public String toString() {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < k; i++) {
            t.append("'");
        }
        return d.toString() + t.toString();
    }

    @Override
    public Expr changeVarTo(String var, Expr other) {
        return new Numb(k, d.changeVarTo(var, other));
    }
}
