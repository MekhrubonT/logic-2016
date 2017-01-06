package helper;

import expression.*;

/**
 * Created by Lenovo on 05.01.2017.
 */
public class Helper {
    public static final Var varA = new Var("a");
    public static final Var varB = new Var("b");
    public static final Var varC = new Var("c");
    public static final Var zero = new Var("0");
    public static Expr helper = con(eqv(zero, zero), con(eqv(zero, zero), eqv(zero, zero)));

    static public Expr canonicAxiom1 = con(eqv(varA, varB), con(eqv(varA, varC), eqv(varB, varC)));
    static public Expr canonicAxiom2 = con(eqv(varA, varB), eqv(plk(varA, 1), plk(varB, 1)));
    static public Expr canonicAxiom4 = neg(eqv(plk(varA, 1), zero));
    static public Expr canonicAxiom5 = eqv(plus(varA, zero), varA);
    static public Expr canonicAxiom6 = eqv(plus(varA, plk(varB, 1)), plk(plus(varA, varB), 1));

    public static Expr neg(Expr a) {
        return new Negate(a);
    }
    public static Expr and(Expr a, Expr b) {
        return new BiFunc(a, b, "&");
    }
    public static Expr con(Expr a, Expr b) {
        return new BiFunc(a, b, "->");
    }
    public static Expr eqv(Expr a, Expr b) {
        return new BiFunc(a, b, "=");
    }
    public static Expr plus(Expr a, Expr b) {
        return new BiFunc(a, b, "+");
    }
    public static Expr l(Expr a, Expr b) {
        return new BiFunc(a, b, "<");
    }
    public static Expr le(Expr a, Expr b) {
        return new BiFunc(a, b, "<=");
    }
    public static Expr plk(Expr a, int k) {
        return new Numb(k, a);
    }
    public static Expr numb(int n) {
        return new Numb(n, zero);
    }
    public static Expr quant(String q, Expr a, Var var) {
        return new Quantor(q, var, a);
    }
}
