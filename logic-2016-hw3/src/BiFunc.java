/**
 * Created by Lenovo on 05.01.2017.
 */
public class BiFunc extends Expr {
    final Expr a, b;
    final String op;



    public BiFunc(Expr a, Expr b, String op) {
        this.a = a;
        this.b = b;
        this.op = op;
    }

    @Override
    public String toString() {
        return "(" + a.toString() + op + b.toString() + ")";
    }

    @Override
    public Expr changeVarTo(String var, Expr other) {
        return new BiFunc(a.changeVarTo(var, other),
                b.changeVarTo(var, other), op);
    }
}
