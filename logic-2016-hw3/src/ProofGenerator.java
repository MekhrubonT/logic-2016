import java.io.PrintWriter;

/**
 * Created by Lenovo on 05.01.2017.
 */
public class ProofGenerator {
    final static boolean LESS = false, LESSOREQUAL = true;
    final PrintWriter out;
    ProofGenerator(PrintWriter out) {
        this.out = out;
    }

    static final Var varA = new Var("a");
    static final Var varB = new Var("b");
    static final Var varC = new Var("c");
    static final Var zero = new Var("0");

    static private Var z1 = new Var("z1");
    static private Var z2 = new Var("z2");
    static private Var z3 = new Var("z3");
    static Expr axiom6 = new BiFunc(new BiFunc(z1, zero, "+"), z1, "=");
    static Expr axiom2 = con(eqv(z1, z2), con(eqv(z1, z3), eqv(z2, z3)));

    static Expr canonicAxiom1 = con(eqv(z1, z2), con(eqv(z1, z3), eqv(z2, z3)));
    static Expr canonicAxiom2 = con(eqv(z1, z2), eqv(plk(z1, 1), plk(z2, 1)));
    static Expr canonicAxiom5 = eqv(plus(z1, zero), z1);
    static Expr canonicAxiom6 = eqv(plus(z1, plk(z2, 1)), plk(plus(z1, z2), 1));
    private static Expr helper = eqv(plus(zero, zero), zero);



    Expr addQuantors(Expr d, Var[] vars) {
        for (Var var : vars) {
            d = quant("@", d, var);
            out.println(con(helper, d));
        }
        out.println(d);
        return d;
    }

    void prepareForQuantors(Expr canAxiom) {
        out.println(canAxiom);
        out.println(con(canAxiom, con(helper, canAxiom)));
        out.println(con(helper, canAxiom));
    }


    Expr gen(Expr canAxiom, Expr[] args, Var quant[]) {
        assert args.length == quant.length;
        prepareForQuantors(canAxiom);
        Expr cur = addQuantors(canAxiom, quant);
        for (int i = args.length - 1; i >= 0; --i) {
            Expr nx = cur.freeQuantor(args[i]);
            out.println(con(cur, nx));
            out.println(nx);
            cur = nx;
        }
        return cur;

    }

    Expr genAxiom1(Expr a, Expr b, Expr c) {
        return gen(canonicAxiom1, new Expr[]{a, b, c}, new Var[]{z1, z2, z3});
    }
    Expr genAxiom2(Expr a, Expr b) {
        return gen(canonicAxiom2, new Expr[]{a, b}, new Var[]{z1, z2});
    }
    Expr genAxiom5(Expr a) {
        return gen(canonicAxiom5, new Expr[]{a}, new Var[]{z1});
    }
    Expr genAxiom6(Expr a, Expr b) {
        return gen(canonicAxiom6, new Expr[]{a, b}, new Var[]{z1, z2});
    }
    void aa(Expr a) {
        Expr f = genAxiom5(a);
        Expr b = genAxiom1(f, f, a);
        out.println(b);
        out.println(con(f, eqv(a, a)));
        out.println(eqv(a, a));
    }

    Expr abba(Expr a, Expr b) {
        out.println(eqv(a, b));
        aa(a);
        genAxiom1(a, b, a);
        out.println(con(eqv(a, a), eqv(b, a)));
        out.println(eqv(b, a));
        return eqv(b, a);
    }

    Expr abbcac(Expr a, Expr b, Expr c) {
        genAxiom1(b, a, c);
        abba(a, b);
        out.println(con(eqv(b, c), eqv(a, c)));
        out.println(eqv(a, c));
        return eqv(a, c);
    }
    Expr abcbac(Expr a, Expr b, Expr c) {
        abba(c, b);
        return abbcac(a, b, c);
    }

    // a=b->a+s=b+s
    void lemmaE(Expr t, Expr r, Expr s) {
        out.println("ProofGenerator.lemmaE - skipped");
        out.println(eqv(x1, x2) + "|-" + eqv(plus(x1, zero), plus(x2, zero)));
        genAxiom5(x1);
        genAxiom5(x2);
        out.println(eqv(x1, x2));
//
        abbcac(plus(x1, zero), x1, x2);
        Expr a0 = abcbac(plus(x1, zero), x2, plus(x2, zero));
        // DEDUCTION NEED
        out.println("Deduction");

        out.println(con(eqv(x1, x2), eqv(plus(x1, x3), plus(x2, x3))) + "," + eqv(x1, x2) + "|-" + eqv(plus(x1, plk(x3, 1)), plus(x2, plk(x3, 1))));
        out.println(con(eqv(x1, x2), eqv(plus(x1, x3), plus(x2, x3))));
        out.println(eqv(x1, x2));
//
        genAxiom6(x1, x3);
        genAxiom6(x2, x3);
        out.println(eqv(plus(x1, x3), plus(x2, x3)));
        genAxiom2(plus(x1, x3), plus(x2, x3));
        out.println(eqv(plk(plus(x1, x3), 1), plk(plus(x2, x3), 1)));
//
        abbcac(plus(x1, plk(x3, 1)), plk(plus(x1, x3), 1), plk(plus(x2, x3), 1));
        Expr ax = abcbac(plus(x1, plk(x3, 1)), plk(plus(x2, x3), 1), plus(x2, plk(x3, 1)));
        // DEDUCTION NEED
        out.println("Deduction");
//        Expr a0 =
//        Expr ax =
        a0 = con(eqv(x1, x2), a0);
        out.println(a0);
        ax = con(eqv(x1, x2), ax);
        out.println(ax);
        Expr phi = con(eqv(x1, x2), eqv(plus(x1, x3), plus(x2, x3)));
        Induction(phi, a0, ax, x3);

//
        gen(phi, new Expr[]{t, r, s}, new Var[]{x1, x2, x3});
        out.println(eqv(plus(t, s), plus(r, s)));
    }

    private void Induction(Expr phi, Expr a0, Expr ax, Var var) {
        out.println(phi);
        out.println(con(ax, con(phi, ax)));
        ax = con(phi, ax);
        prepareForQuantors(ax);
        Expr qax = addQuantors(ax, new Var[]{var});
        out.println(con(a0, con(qax, and(a0, qax))));
        out.println(con(qax, and(a0, qax)));
        out.println(and(a0, qax));
        out.println(con(and(a0, qax), phi));
    }

    static Var x1 = new Var("x1");
    static Var x2 = new Var("x2");
    static Var x3 = new Var("x3");
    // t = 0 + t
    void lemmaF(Expr t) {
        out.println(eqv(plus(zero, zero), zero));
        Expr a0 = abba(plus(zero, zero), zero);
        out.println("Deduction");
        out.println(eqv(x1, plus(zero, x1)));
        genAxiom6(zero, x1);
        genAxiom2(x1, plus(zero, x1));
        out.println(eqv(plk(x1, 1), plk(plus(zero, x1), 1)));
        Expr ax = abcbac(plk(x1, 1), plk(plus(zero, x1), 1), plus(zero, plk(x1, 1)));
        out.println("Deduction");

        out.println(a0);
        out.println(ax);
        Expr phi = eqv(plus(x1, zero), x1);
        Induction(phi, a0, ax, x1);

        gen(phi, new Expr[]{t}, new Var[]{x1});
    }

    // x'+y=(x+y)'
    void lemmaG(Expr t, Expr r) {
        genAxiom5(plk(x1, 1));
        genAxiom5(x1);
        genAxiom2(plus(x1, zero), x1);
        out.println(eqv(plk(plus(x1, zero), 1), plk(x1, 1)));
        abcbac(plus(plk(x1, 1), zero), plk(plus(x1, zero), 1), plk(x1, 1));
        out.println("Deduction");

        out.println(eqv(plus(plk(x1, 1), x2), plk(plus(x1, x2), 1)));
        genAxiom6(plk(x1, 1), x2);
        genAxiom2(plus(plk(x1, 1), (x2)), plk(plus(x1, x2), 1));
        out.println(eqv(plk(plus(plk(x1, 1), (x2)), 1), plk(plus(x1, x2), 2)));
        abbcac(plus(plk(x1, 1), plk(x2, 1)), plk(plus(plk(x1, 1), (x2)), 1), plk(plus(x1, x2), 2));
        BiFunc res = (BiFunc) genAxiom6(x1, x2);
        res = (BiFunc) genAxiom2(res.a, res.b);
        out.println(res.b);
        abcbac(plus(plk(x1, 1), plk(x2, 1)), plk(plus(x1, x2), 2), plk(plus(x1, plk(x2, 1)), 1));
        out.println("Deduction");
//        abcbac(, plk(plus(x1, x2), 2), )
//        out.println(eqv(res, plk(res, 1)));
//        out.println(, plk(plus(x1, x2), 1)));
    }





    void aLessOrEqualB(int ka, int kb, boolean flag) {
        Numb a = new Numb(ka, zero);
        Numb b = new Numb(kb, zero);

        out.println(eqv(plus(a, zero), a));
        for (int tt = 0; tt < b.k - a.k; ++tt) {
            Expr t = numb(tt);
            out.println(con(eqv(plus(a, t), numb(a.k + tt)), eqv(plk(plus(a, t), 1), plk(numb(a.k + tt), 1))));

            Expr d2 = eqv(plk(plus(a, t), 1), plk(numb(a.k + tt), 1));
            out.println(d2);

            mPapbPsEQVapbs(a, t);
            Expr d1 = eqv(plk(plus(a, t), 1), plus(a, plk(t, 1)));
            Expr d3 = eqv(plus(a, plk(t, 1)), plk(numb(a.k + tt), 1));
            out.println(con(d1, con(d2, d3)));
            out.println(con(d2, d3));
            out.println(d3);
        }
//        String s, d;
        Expr s = eqv(plus(a, numb(b.k - a.k)), b);
        Expr d;
        if (flag == LESS) {
            Expr ns = neg(eqv(numb(b.k - a.k), zero));
            out.println(ns);
            out.println(con(ns, con(s, and(s, ns))));
            out.println(con(s, and(s, ns)));
            out.println(and(s, ns));
            s = and(s, ns);
            d = and(quant("?", eqv(plus(a, z1), b), z1), neg(eqv(z1, zero)));
        } else {
            d = quant("?", eqv(plus(a, z1), b), z1);
        }
        out.println(con(s, d));
        out.println(d);
    }

    Expr zeroEqvZero = new BiFunc(zero, zero, "=");
    Expr ax2 = con(zeroEqvZero, con(zeroEqvZero, zeroEqvZero));
//
    static Expr neg(Expr a) {
        return new Negate(a);
    }
    static Expr and(Expr a, Expr b) {
        return new BiFunc(a, b, "&");
    }
    static Expr con(Expr a, Expr b) {
        return new BiFunc(a, b, "->");
    }
    static Expr eqv(Expr a, Expr b) {
        return new BiFunc(a, b, "=");
    }
    static Expr plus(Expr a, Expr b) {
        return new BiFunc(a, b, "+");
    }
    static Expr l(Expr a, Expr b) {
        return new BiFunc(a, b, "<");
    }
    static Expr le(Expr a, Expr b) {
        return new BiFunc(a, b, "<=");
    }
    static Expr plk(Expr a, int k) {
        return new Numb(k, a);
    }
    static Expr numb(int n) {
        return new Numb(n, zero);
    }
    static Expr quant(String q, Expr a, Var var) {
        return new Quantor(q, var, a);
    }


//
    Expr axiom5 = con(plus(z1, plk(z2, 1)), plk(plus(z1, z2), 1));
    void mPapbPsEQVapbs(Expr a, Expr b) {
        prepareForQuantors(axiom5);
        Expr axiom5WithQuantors = addQuantors(axiom5, new Var[]{z1, z2});
        Expr nx1 = axiom5WithQuantors.freeQuantor(b);
        out.println(con(axiom5WithQuantors, nx1));
        out.println(nx1);
        Expr nx2 = nx1.freeQuantor(a);
        out.println(con(nx1, nx2));
        out.println(nx2);
        Expr apbs = plus(a, plk(b, 1));
        Expr PapbPs = plk(plus(a, b), 1);
        aa(apbs);
        out.println(con(eqv(apbs, PapbPs), con(eqv(apbs, apbs), eqv(PapbPs, apbs))));
        out.println(con(eqv(apbs, apbs), eqv(PapbPs, apbs)));
        out.println(eqv(PapbPs, apbs));
    }


//
//    void commutativity() {
//        for (int c = 1; c <= 2; ++c) {
//            out.println("0+" + print(c) + "=(0+" + print(c - 1) + ")'");
//            abba("0+" + print(c), "(0+" + print(c - 1) + ")'");
//            out.println("(0+" + print(c - 1) + ")'=" + "(" + print(c - 1) + "+0)'");
//            axiom2WithMp("(0+" + print(c - 1) + ")'", "0+" + print(c), "(" + print(c - 1) + "+0)'");
//            out.println("(" + print(c - 1) + "+0)'=" + print(c));
//            out.println(print(c) + "+0=" + print(c));
//            abba("(" + print(c - 1) + "+0)'", print(c));
//            abba(print(c) + "+0", print(c));
//            axiom2WithMp(print(c), "(" + print(c - 1) + "+0)'", print(c) + "+0");
//            abba("0+" + print(c), "(" + print(c - 1) + "+0)'");
//            axiom2WithMp("(" + print(c - 1) + "+0)'", "0+" + print(c), print(c) + "+0");
//        }
//    }
//
//    String op(int a, int b, String s) {
//        return print(a) + s + print(b);
//    }
//
    private static Var w = new Var("w");
    private static Var v = new Var("v");
    private static Var u = new Var("u");





    // a=b->s+a=s+b
    void lemma2() {

    }

    void aLbConbLecConaLc() {

//        out.println(l(z1, z2) + "," + le(z2, z3) + "|-" + l(z1, z3));
//        out.println(l(z1, z2));
//        out.println(le(z2, z3));
//
//        Expr th = and(neg(eqv(w, zero)), eqv(plus(z1, w), z2));
//        Expr fr = eqv(plus(z2, v), z3);
//        Expr qth = quant("?", th, w);
//        Expr qfr = quant("?", fr, v);
//        out.println(qth + "\n" + qfr);
//        out.println(con(qth, th) + "\n" + con(qfr, fr));
//        out.println(th + "\n" + fr);
//        out.println(con(th, neg(eqv(w, zero))));
//        out.println(con(th, eqv(plus(z1, w), z2)));
//        out.println(neg(eqv(w, zero)));
//        out.println(eqv(plus(z1, w), z2));
//
//        lemmaE(plus(t, w), r, v);
////
//        abbcac(plus(plus(z1, w), v), plus(z2, v), z3);
//
//        associativity(z1, w, v, z3);
//        out.println("Skipped");
//        out.println(neg(eqv(plus(w, v), zero)));
//        out.println(and(neg(eqv(plus(w, v), zero)), eqv(plus(z1, plus(w, v)), z3)));
//        out.println(quant("?", and(neg(eqv(u, zero)), eqv(plus(z1, u), z3)), u));

//        Expr lemma = con(l(z1, z2), con(le(z2, z3), l(z1, z3)));
//        addQuantors(lemma, new Var[]{z1, z2, z3});
    }

    private void associativity(Var z1, Var w, Var v, Var z3) {
        out.println("ProofGenerator.associativity - skipped");
        out.println(eqv(plus(z1, plus(w, v)), z3));
    }

    //
    void notALessOrEqualB(int na, int nb) {
//        Numb a = new Numb(na, zero);
//        Numb b = new Numb(nb, zero);
//        out.println(neg(eqv(a, b)) + "," + le(b, a) + "|-" + neg(le(a, b)));
//        aLessOrEqualB(nb, na, LESS);
        aLbConbLecConaLc();
    }


//
}
