import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Lenovo on 03.01.2017.
 */
public class Main {
    static PrintWriter out;
    public static void main(String[] args) throws FileNotFoundException {
        out = new PrintWriter(new File("output.txt"));
        int n = 3;
        mPapbPsEQVapbs("a", "b");
        for (int a = 0; a < n; a++) {
            for (int b = a; b < n; b++) {
                aLessB(a, b);
            }
        }
//        aLessB(0, 1);
        out.close();
    }

    static String print(int a) {
        StringBuilder s = new StringBuilder("0");
        while (a-- > 0) s.append("'");
        return s.toString();
    }

    static void aLessB(int a, int b) {
        out.println(print(a) + "+" + print(0) + "=" + print(a));
        for (int t = 0; t <= b - a; ++t) {
            if (t < b - a) {
                out.println(
                        "(" + print(a) + "+" + print(t) + "=" + print(a + t) + ")->" +
                                "(" + print(a) + "+" + print(t) + ")'=" + print(a + t) + "'");
                String d2 = "(" + print(a) + "+" + print(t) + ")'=" + print(a + t) + "'";
                out.println(d2);

                mPapbPsEQVapbs(print(a), print(t));
                String d1 = "(" + print(a) + "+" + print(t) + ")'=(" + print(a) + "+" + print(t) + "')";

                String d3 = "(" + print(a) + "+" + print(t) + "')=" + print(a + t) + "'";
                out.println(con(d1, con(d2, d3)));
                out.println(con(d2, d3));
                out.println(d3);
            }
        }
        out.println("(" + print(a) + "+" + print(b - a) + "=" + print(b) + ")->"
                + "?t(" + print(a) + "+t=" + print(b) + ")");
        out.print("?t(" + print(a) + "+t=" + print(b) + ")");
        out.println("\t\t//" + a + "<=" + b);

    }

    static String ax2 = "0=0->0=0->0=0";

    static String con(String a, String b) {
        return "(" + a + ")->(" + b + ")";
    }
    static String forall(String a, String var) {
        return "@" + var + "(" + a + ")";
    }

    static void prepareForQuantors(String d) {
        out.println(ax2);
        out.println(con(d, con(ax2, d)));
        out.println(con(ax2, d));
    }

    static void aEqvA(String a) {
        String axiom6 = "a+0=a";
        prepareForQuantors(axiom6);
        String axiom6WithQuantors = addQuantors(axiom6, new String[]{"a"});


        String axiom2 = "a=b->a=c->b=c";
        prepareForQuantors(axiom2);
        String axiom2WithQuantors = addQuantors(axiom2, new String[]{"aaa", "bbb", "ccc"});

        String fin = "(" + a + ")+0=(" + a + ")";
        out.println(con(forall(axiom6, "a"), fin));
        out.println(fin);

        String res = "(" + a + ")=(" + a + ")";

        String n1 = "@bbb(@aaa(aaa=bbb->aaa=" + a + "->bbb=" + a + "))";
        out.println(con(axiom2WithQuantors, n1));
        out.println(n1);
        String n2 = "@aaa(aaa=" + a + "->aaa=" + a + "->" + res + ")";
        out.println(con(n1, n2));
        out.println(n2);
        String n3 = "" + fin + "->" + fin + "->" + res;
        out.println(con(n2, n3));
        out.println(n3);



        out.println(con(fin, con(fin, res)));
        out.println(con(fin, res));
        out.println(res);
    }

    static String addQuantors(String d, String[] vars) {
        for (String var : vars) {
            d = forall(d, var);
            out.println(con(ax2, d));
        }
        out.println(d);
        return d;
    }

    static String axiom5 = "a+b'=(a+b)'";
    static void mPapbPsEQVapbs(String a, String b) {
        a = "(" + a + ")";
        b = "(" + b + ")";

        prepareForQuantors(axiom5);
        String axiom5WithQuantors = addQuantors(axiom5, new String[]{"aaa", "bbb"});
        String nx1 = "@aaa(a+" + b + "'=(a+" + b + ")')";
        out.println(con(axiom5WithQuantors, nx1));
        out.println(nx1);
        String nx2 = a + "+" + b + "'=(" + a + "+" + b + ")')";
        out.println(con(nx1, nx2));
        out.println(nx2);


        String apbs = a + "+" +  b + "'";
        String PapbPs = "(" + a + "+" +  b + ")'";

        aEqvA(apbs);

        out.println(con(apbs + "=" + PapbPs, con(apbs + "=" + apbs, PapbPs + "=" + apbs)));
        out.println(con(apbs + "=" + apbs, PapbPs + "=" + apbs));
        out.println(PapbPs + "=" + apbs);
    }
}
