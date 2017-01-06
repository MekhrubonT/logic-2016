import expression.BiFunc;
import expression.Expr;
import expression.Numb;
import expression.Var;


import java.io.*;

import static helper.Helper.*;

public class ProofGenerator {
    final static boolean LESS = false, LESSOREQUAL = true;
    final AxiomGenerator gen;
    final PrintWriter out;
    ProofGenerator(PrintWriter out) {
        this.out = out;
        gen = new AxiomGenerator(out);
    }

//
    void proveHelper(char[] c, Expr[] a, String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s = in.readLine();
        while (s != null) {
            for (int i = 0; i < s.length(); i++) {
                boolean flag = true;
                for (int j = 0; j < c.length; j++) {
                    if (s.charAt(i) == c[j]) {
                        out.print(a[j]);
                        flag = false;
                    }
                }
                if (flag) {
                    out.print(s.charAt(i));
                }
            }
            out.println();
            s = in.readLine();
        }

    }

//    void genAA(Expr a) throws IOException {
//        proveHelper(new char[]{'a'}, new Expr[]{a}, "aa");
//    }
    Expr genABBA(Expr a, Expr b) throws IOException {
        proveHelper(new char[]{'a', 'b'}, new Expr[]{a, b}, "abba.txt");
        return eqv(b, a);
    }
//    void aa(Expr a) {
//        out.println("|-");
//        out.println(helper);
//        Expr f = gen.genAxiom5(a);
//        Expr b = gen.genAxiom1(plus(a, zero), a, a);
//        out.println(con(f, eqv(a, a)));
//        out.println(eqv(a, a));
//    }
//
//    void abba(Expr a, Expr b) throws IOException {
//        out.println("|-(a=b)->(b=a)");
//        out.println(helper);
////        out.println(eqv(a, b));
//        genAA(a);
//        gen.genAxiom1(a, b, a);
//        out.println(con(eqv(a, a), eqv(b, a)));
//        out.println(eqv(b, a));
//    }
//
//    Expr abbcac(Expr a, Expr b, Expr c) throws IOException {
//        out.println(eqv(a, b) + "," + eqv(b, c) + "|-" + eqv(a, c));
//        out.println(eqv(a, b) + "\n" + eqv(b, c));
//        out.println(helper);
//        gen.genAxiom1(b, a, c);
//        genABBA(a, b);
//        out.println(eqv(b, a));
//        out.println(con(eqv(b, c), eqv(a, c)));
//        out.println(eqv(a, c));
//        return eqv(a, c);
//    }
//    Expr abcbac(Expr a, Expr b, Expr c) throws IOException {
//
//        genABBA(c, b);
//        return abbcac(a, b, c);
//    }
//
//    // a=b->a+s=b+s
//
//    static private Var e1 = new Var("e1");
//    static private Var e2 = new Var("e2");
//    static private Var e3 = new Var("e3");
//    boolean isLemmaEProved = false;
//    void lemmaE(Expr t, Expr r, Expr s) throws IOException {
//        Expr phi = con(eqv(e1, e2), eqv(plus(e1, e3), plus(e2, e3)));
//        if (!isLemmaEProved) {
//            out.println("ProofGenerator.lemmaE - skipped");
//            out.println(eqv(e1, e2) + "|-" + eqv(plus(e1, zero), plus(e2, zero)));
//            gen.genAxiom5(e1);
//            gen.genAxiom5(e2);
//            out.println(eqv(e1, e2));
////
//            abbcac(plus(e1, zero), e1, e2);
//            Expr a0 = abcbac(plus(e1, zero), e2, plus(e2, zero));
//            // DEDUCTION NEED
//            out.println("deduction");
//
//            out.println(con(eqv(e1, e2), eqv(plus(e1, e3), plus(e2, e3))) + "," + eqv(e1, e2) + "|-" + eqv(plus(e1, plk(e3, 1)), plus(e2, plk(e3, 1))));
//            out.println(con(eqv(e1, e2), eqv(plus(e1, e3), plus(e2, e3))));
//            out.println(eqv(e1, e2));
////
//            gen.genAxiom6(e1, e3);
//            gen.genAxiom6(e2, e3);
//            out.println(eqv(plus(e1, e3), plus(e2, e3)));
//            gen.genAxiom2(plus(e1, e3), plus(e2, e3));
//            out.println(eqv(plk(plus(e1, e3), 1), plk(plus(e2, e3), 1)));
////
//            abbcac(plus(e1, plk(e3, 1)), plk(plus(e1, e3), 1), plk(plus(e2, e3), 1));
//            Expr ax = abcbac(plus(e1, plk(e3, 1)), plk(plus(e2, e3), 1), plus(e2, plk(e3, 1)));
////        // DEDUCTION NEED
//            out.println("deduction");
//            ax = con(eqv(e1, e2), ax);
//            out.println(ax);
//            induction(phi, a0, ax, e3);
//            isLemmaEProved = true;
//        }
//        gen.gen(phi, new expression.Expr[]{t, r, s}, new expression.Var[]{e1, e2, e3});
//        out.println(eqv(plus(t, s), plus(r, s)));
//    }
//
    private void induction(Expr phi, Expr a0, Expr ax, Var var) {
        out.println(a0);
        ax = con(phi, ax);
        gen.prepareForQuantors(ax);
        Expr qax = gen.addQuantors(ax, new Var[]{var});
        out.println(con(a0, con(qax, and(a0, qax))));
        out.println(con(qax, and(a0, qax)));
        out.println(and(a0, qax));
        out.println(con(and(a0, qax), phi));
    }

    static private Var f1 = new Var("f1");
    // t = 0 + t
    boolean isLemmaFProved= false;
    void lemmaF(Expr t) throws IOException {
        out.println(helper);
        Expr phi = eqv(f1, plus(zero, f1));
        if (!isLemmaFProved) {
            gen.genAxiom5(zero);
            Expr a0 = abba2(plus(zero, zero), zero);
            a0 = ((BiFunc) a0).b;
//            out.println("deduction");
            out.println(phi + "|-(f1'=(0+f1'))");
            out.println(phi);
            gen.genAxiom6(zero, f1);
            gen.genAxiom2(f1, plus(zero, f1));
            out.println(eqv(plk(f1, 1), plk(plus(zero, f1), 1)));
            Expr ax = abcbac(plk(f1, 1), plk(plus(zero, f1), 1), plus(zero, plk(f1, 1)));
            out.println("deduction");
//
            out.println(a0);
            out.println(ax);
            induction(phi, a0, ax, f1);
            isLemmaFProved = true;
        }
        gen.gen(phi, new Expr[]{t}, new Var[]{f1});
    }

//    // x'+y=(x+y)'
    static private Var g1 = new Var("g1");
    static private Var g2 = new Var("g2");
    boolean lemmaGProved = false;
    void lemmaG(Expr t, Expr r) throws IOException {
        Expr hyp = eqv(plus(plk(g1, 1), g2), plk(plus(g1, g2), 1));
        if (!lemmaGProved) {
            gen.genAxiom5(plk(g1, 1));
            gen.genAxiom5(g1);
            gen.genAxiom2(plus(g1, zero), g1);
            out.println(eqv(plk(plus(g1, zero), 1), plk(g1, 1)));
            Expr a0 = abcbac(plus(plk(g1, 1), zero), plk(g1, 1), plk(plus(g1, zero), 1));

            Expr pro = eqv(plus(plk(g1, 1), plk(g2, 1)), plk(plus(g1, plk(g2, 1)), 1));
            out.println(hyp + "|-" + pro);
            out.println(eqv(plus(plk(g1, 1), g2), plk(plus(g1, g2), 1)));
            gen.genAxiom6(plk(g1, 1), g2);
            gen.genAxiom2(plus(plk(g1, 1), (g2)), plk(plus(g1, g2), 1));
            out.println(eqv(plk(plus(plk(g1, 1), (g2)), 1), plk(plus(g1, g2), 2)));
            abbcac(plus(plk(g1, 1), plk(g2, 1)), plk(plus(plk(g1, 1), (g2)), 1), plk(plus(g1, g2), 2));
            BiFunc res = (BiFunc) gen.genAxiom6(g1, g2);
            res = (BiFunc) gen.genAxiom2(res.a, res.b);
            out.println(res.b);
            Expr d = abcbac(plus(plk(g1, 1), plk(g2, 1)), plk(plus(g1, g2), 2), plk(plus(g1, plk(g2, 1)), 1));
            out.println("deduction");
            induction(hyp, a0, pro, g2);
            lemmaGProved = true;
        }
        gen.gen(hyp, new Expr[]{t, r}, new Var[]{g1, g2});
    }
//
//    static private Var h1 = new Var("h1");
//    static private Var h2 = new Var("h2");
//    private boolean isLemmaHProved = false;
//    void lemmaH(Expr a, Expr b) throws IOException {
//        Expr phi = eqv(plus(h1, h2), plus(h2, h1));
//        if (!isLemmaHProved) {
//            gen.genAxiom5(h1);
//            lemmaF(h1);
//            Expr a0 = abbcac(plus(h1, zero), h1, plus(zero, h1));
//
//            out.println(phi + "|-" + eqv(plus(h1, plk(h2, 1)), plus(plk(h2, 1), h1)));
//            out.println(phi);
//            gen.genAxiom6(h1, h2);
//            lemmaG(h2, h1);
//            gen.genAxiom2(plus(h1, h2), plus(h2, h1));
//            out.println(eqv(plk(plus(h1, h2), 1), plk(plus(h2, h1), 1)));
//            isLemmaHProved = true;
//            abbcac(plus(h1, plk(h2, 1)), plk(plus(h1, h2), 1), plk(plus(h2, h1), 1));
//            Expr ax = abcbac(plus(h1, plk(h2, 1)), plk(plus(h2, h1), 1), plus(plk(h2, 1), h1));
//            out.println("deduction");
//            induction(phi, a0, ax, h2);
//        }
//        gen.gen(phi, new Expr[]{a, b}, new Var[]{h1, h2});
//
//    }
//
//
//    static private Var i1 = new Var("i1");
//    static private Var i2 = new Var("i2");
//    static private Var i3 = new Var("i3");
//    private boolean isLemmaIProved = false;
//    void lemmaI(Expr a, Expr b, Expr c) throws IOException {
//        Expr phi = con(eqv(i1, i2), eqv(plus(i3, i1), plus(i3, i2)));
//        if (!isLemmaIProved) {
//            out.println(eqv(i1, i2) + "|-" + eqv(plus(i3, i1), plus(i3, i2)));
//            out.println(eqv(i1, i2));
//            lemmaE(i1, i2, i3);
//            lemmaH(i1, i3);
//            lemmaH(i2, i3);
//            out.println(eqv(plus(i1, i3), plus(i2, i3)));
//            BiFunc temp = (BiFunc) gen.genAxiom1(plus(i1, i3), plus(i3, i1), plus(i2, i3));
//            out.println(temp.b);
//            temp = (BiFunc) temp.b;
//            out.println(temp.b);
//
//            abcbac(plus(i3, i1), plus(i2, i3), plus(i3, i2));
//            out.println("deduction");
//            isLemmaIProved = true;
//        }
//        gen.gen(phi, new Expr[]{a, b, c}, new Var[]{i1, i2, i3});
//    }
//
//    static private Var j1 = new Var("h1");
//    static private Var j2 = new Var("h2");
//    static private Var j3 = new Var("h3");
//    private boolean isLemmaJProved = false;
//    void lemmaJ(Expr a, Expr b, Expr c) throws IOException {
//        BiFunc phi = (BiFunc) eqv(plus(plus(j1, j2), j3), plus(j1, plus(j2, j3)));
//        if (!isLemmaJProved) {
//            gen.genAxiom5(plus(j1, j2));
//            gen.genAxiom5(j2);
//            lemmaI(plus(j2, zero), j2, j1);
//            Expr a0 = abcbac(plus(plus(j1, j2), zero), plus(j1, j2), plus(j1, plus(j2, zero)));
//
//            out.println(phi + "|-" + eqv(plus(plus(j1, j2), plk(j3, 1)), plus(j1, plus(j2, plk(j3, 1)))));
//            gen.genAxiom6(plus(j1, j2), j3);
//            gen.genAxiom2(phi.a, phi.b);
//            out.println(eqv(plk(phi.a, 1), plk(phi.b, 1)));
//            abbcac(plus(plus(j1, j2), plk(j3, 1)), plk(phi.a, 1), plk(phi.b, 1));
//            gen.genAxiom6(j2, j3);
//            lemmaI(plus(j2, plk(j3, 1)), plk(plus(j2, j3), 1), j1);
//            out.println(eqv(plus(j1, plus(j2, plk(j3, 1))), plus(j1, plk(plus(j2, j3), 1))));
//            BiFunc temp = (BiFunc) abbcac(plus(j1, plus(j2, plk(j3, 1))), plus(j1, plk(plus(j2, j3), 1)), plk(plus(j1, plus(j2, j3)), 1));
//            Expr ax = abcbac(plus(plus(j1, j2), plk(j3, 1)), temp.b, temp.a);
//
//            induction(phi, a0, ax, j3);
//            isLemmaJProved = true;
//        }
//        gen.gen(phi, new Expr[]{a, b, c}, new Var[]{j1, j2, j3});
//    }
//
//
//    static private Var t1 = new Var("t1");
//    static private Var t2 = new Var("t2");
//    private boolean isLemmaZProved = false;
//    void lemmaZ(Expr a, Expr b) {
//        Expr phi = neg(eqv(plus(t1, t2), zero));
//        if (!isLemmaZProved) {
//
//            isLemmaZProved = true;
//        }
////        gen.gen(phi, new Expr[]{a, b}, new Var[]{t1, t2});
//    }
//
    void aLessOrEqualB(int ka, int kb, boolean flag) throws IOException {
        Numb a = new Numb(ka, zero);
        Numb b = new Numb(kb, zero);

        gen.genAxiom5(a);
        for (int tt = 0; tt < b.k - a.k; ++tt) {
            Expr t = numb(tt);
            gen.genAxiom2(plus(a, t), numb(a.k + tt));
//            out.println(con(eqv(plus(a, t), numb(a.k + tt)), eqv(plk(plus(a, t), 1), plk(numb(a.k + tt), 1))));
//            break;
            Expr d2 = eqv(plk(plus(a, t), 1), plk(numb(a.k + tt), 1));
            out.println(d2);
//
            mPapbPsEQVapbs(a, t);
//            Expr d1 = eqv(plk(plus(a, t), 1), plus(a, plk(t, 1)));
            Expr d3 = eqv(plus(a, plk(t, 1)), plk(numb(a.k + tt), 1));
//            out.println(d1);
//            out.println(d2);
//            out.println(d3);
            gen.genAxiom1(plk(plus(a, t), 1), plus(a, plk(t, 1)), plk(numb(a.k + tt), 1));
//            out.println(con(d1, con(d2, d3)));
            out.println(con(d2, d3));
            out.println(d3);
        }
////        String s, d;
        Expr s = eqv(plus(a, numb(b.k - a.k)), b);
        Expr d;
        if (flag == LESS) {
            Expr ns = neg(eqv(numb(b.k - a.k), zero));
            gen.genAxiom4(numb(b.k - a.k - 1));
            out.println(con(ns, con(s, and(ns, s))));
            out.println(con(s, and(ns, s)));
            out.println(and(ns, s));
            s = and(ns, s);
            d = quant("?", and(neg(eqv(varA, zero)), eqv(plus(a, varA), b)), varA);
        } else {
            d = quant("?", eqv(plus(a, varA), b), varA);
        }
        out.println(con(s, d));
        out.println(d);
    }
//
//    Expr zeroEqvZero = new BiFunc(zero, zero, "=");
//    Expr ax2 = con(zeroEqvZero, con(zeroEqvZero, zeroEqvZero));
////
//
//
////
    void mPapbPsEQVapbs(Expr a, Expr b) throws IOException {
        gen.genAxiom6(a, b);
        Expr apbs = plus(a, plk(b, 1));
        Expr PapbPs = plk(plus(a, b), 1);
        aa2(apbs);
        gen.genAxiom1(apbs, PapbPs, apbs);
        out.println(con(eqv(apbs, apbs), eqv(PapbPs, apbs)));
        out.println(eqv(PapbPs, apbs));
    }



    void nless(int aa, int bb) throws IOException {
        Expr a = numb(aa);
        String b = numb(bb).toString().substring(1);
        String c = numb(aa - bb).toString().substring(2);

        BufferedReader in = new BufferedReader(
                new FileReader("D:\\logic-2016\\logic-2016-hw3\\src\\data\\base.in"));
        String s = in.readLine();
        while (s != null) {
            out.println(s);
            s = in.readLine();
        }
        in = new BufferedReader(
                new FileReader("D:\\logic-2016\\logic-2016-hw3\\src\\data\\LemmaG.txt"));
        s = in.readLine();
        while (s != null) {
            out.println(s);
            s = in.readLine();
        }

        in = new BufferedReader(
                new FileReader("D:\\logic-2016\\logic-2016-hw3\\src\\data\\proof2.in"));
        printer(in, c, b);
        in = new BufferedReader(
                new FileReader("D:\\logic-2016\\logic-2016-hw3\\src\\data\\Morgan.in"));
        printer(in, c, b);
    }

    void printer(BufferedReader in, String c, String b) throws IOException {
        String s = in.readLine();
        while (s != null) {
            for (int i = 0; i < s.length(); i++) {
                switch (s.charAt(i)) {
                    case 'C':
                        out.print(c);
                        break;
                    case 'B':
                        out.print(b);
                        break;
                    default:
                        out.print(s.charAt(i));
                }
            }
            out.println();
            s = in.readLine();
        }
    }

    void aa2(Expr a) {
        gen.gen(eqv(varA, varA), new Expr[]{a}, new Var[]{varA});
    }
    Expr abba2(Expr a, Expr b) {
        return gen.gen(con(eqv(varA, varB), eqv(varB, varA)), new Expr[]{a, b}, new Var[]{varA, varB});
    }

    Expr abbcac(Expr a, Expr b, Expr c) {
        abba2(a, b);
        out.println(eqv(b, a));
        BiFunc temp1 = (BiFunc) gen.genAxiom1(b, a, c);
        BiFunc temp2 = (BiFunc) temp1.b;
        out.println(temp1.b);
        out.println(temp2.b);
        return temp2.b;
    }
    Expr abcbac(Expr a, Expr b, Expr c) {
//        System.out.println(c + " " + b);
        abba2(c, b);
        out.println(eqv(b, c));
        return abbcac(a, b, c);
    }

    void commutativity() throws IOException {
        genABBA(varA, varB);
        aa2(plus(zero, zero));
        int maxN = 10;
        for (int aa = 0; aa <= maxN; ++aa) {
            gen.genAxiom5(numb(aa));
            BiFunc temp = (BiFunc) gen.genAxiom2(plus(numb(aa), zero), numb(aa));
            out.println(temp.b);
        }

        for (int aa = 0; aa < maxN; ++aa) {
            Expr a = numb(aa);
            BiFunc d = (BiFunc) gen.genAxiom2(plus(a, zero), plus(zero, a));
            out.println(d.b);
            BiFunc t = (BiFunc) gen.genAxiom6(zero, a);
            BiFunc temp = (BiFunc) d.b;
            abcbac(temp.b, t.b, t.a);
            abbcac(temp.a, temp.b, t.a);
            abcbac(temp.a, plk(a, 1), plus(plk(a, 1), zero));
            BiFunc res = (BiFunc) gen.genAxiom1(temp.a, plus(plk(a, 1), zero), plus(zero, plk(a, 1)));
            BiFunc resb = (BiFunc) res.b;
            out.println(res.b);
            out.println(resb.b);
//            break;
        }

        for (int bb = 0; bb < maxN; ++bb) {
            Expr b = numb(bb);
            BiFunc tempb = (BiFunc) abba2(plus(plk(b, 1), zero), plus(zero, plk(b, 1)));
            out.println(tempb.b);
            for (int aa = 0; aa < maxN; ++aa) {
                Expr a = numb(aa);
                if (aa < bb) {
                    abba2(plus(plk(b, 1), plk(a, 1)), plus(plk(a, 1), plk(b, 1)));
                    continue;
                }
                BiFunc temp1 = (BiFunc) gen.genAxiom2(plus(plk(a, 1), b), plus(b, plk(a, 1)));
                out.println(temp1.b);
                BiFunc temp2 = (BiFunc) gen.genAxiom6(plk(a, 1), b);
                BiFunc res = (BiFunc) abbcac(temp2.a, temp2.b, ((BiFunc)temp1.b).b);

                System.out.println(a + " " + b);
                BiFunc temp4 = (BiFunc) gen.genAxiom6(b, a);
                BiFunc temp5 = (BiFunc) gen.genAxiom6(a, b);
                BiFunc temp6 = (BiFunc) gen.genAxiom2(plus(a, b), plus(b, a));
                out.println(temp6.b);
                BiFunc temp7 = (BiFunc) abbcac(temp5.a, temp5.b, ((BiFunc)temp6.b).b);
                BiFunc temp8 = (BiFunc) abcbac(temp4.a, temp4.b, temp7.a);
//                BiFunc temp7 = (BiFunc) abcbac(temp4.a, temp4.b, ((BiFunc)temp6.b).a);
//                BiFunc temp8 = (BiFunc) abcbac(temp7.a, temp7.b, temp5.a);
                BiFunc temp9 = (BiFunc) abbcac(temp8.a, temp8.b, plus(plk(b, 1), a));
                BiFunc temp10 = (BiFunc) gen.genAxiom2(temp9.a, temp9.b);
                out.println(temp10.b);
                BiFunc temp11 = (BiFunc) abbcac(res.a, res.b, ((BiFunc)temp10.b).b);
                BiFunc temp12 = (BiFunc) gen.genAxiom6(plk(b, 1), a);
                abcbac(res.a, temp11.b, temp12.a);

            }
//            break;
        }

//        for (int cc = 0; cc < 3; ++cc) {
//            Numb c = (Numb) numb(cc);
//            genABBA(plus(c, zero), c);
////            genABBA(plk(plus(c, zero), 1), plk(c, 1));
//        }

    }

    void aLbConbLecConaLc() {

//        out.println(l(z1, z2) + "," + le(z2, z3) + "|-" + l(z1, z3));
//        out.println(l(z1, z2));
//        out.println(le(z2, z3));
//
//        expression.Expr th = and(neg(eqv(w, zero)), eqv(plus(z1, w), z2));
//        expression.Expr fr = eqv(plus(z2, v), z3);
//        expression.Expr qth = quant("?", th, w);
//        expression.Expr qfr = quant("?", fr, v);
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

//        expression.Expr lemma = con(l(z1, z2), con(le(z2, z3), l(z1, z3)));
//        addQuantors(lemma, new expression.Var[]{z1, z2, z3});
    }

//    private void associativity(Var z1, Var w, Var v, Var z3) {
//        out.println("ProofGenerator.associativity - skipped");
//        out.println(eqv(plus(z1, plus(w, v)), z3));
//    }
//
//    //
//    void notALessOrEqualB(int na, int nb) {
////        expression.Numb a = new expression.Numb(na, zero);
////        expression.Numb b = new expression.Numb(nb, zero);
////        out.println(neg(eqv(a, b)) + "," + le(b, a) + "|-" + neg(le(a, b)));
////        aLessOrEqualB(nb, na, LESS);
//        aLbConbLecConaLc();
//    }
//

//
}
