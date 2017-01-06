import expression.Expr;
import expression.Var;
import static helper.Helper.*;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Lenovo on 05.01.2017.
 */
public class AxiomGenerator {
    final PrintWriter out;
    Expr[] generatedAxioms = new Expr[10];
    HashMap<String, Expr> s;
    AxiomGenerator(PrintWriter out) {
        this.out = out;
        Arrays.fill(generatedAxioms, null);
        s = new HashMap<>();
    }

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
        return gen(canonicAxiom1, new Expr[]{a, b, c}, new Var[]{varA, varB, varC});
    }
    Expr genAxiom2(Expr a, Expr b) {
        return gen(canonicAxiom2, new Expr[]{a, b}, new Var[]{varA, varB});
    }
    Expr genAxiom4(Expr a) {
        return gen(canonicAxiom4, new Expr[]{a}, new Var[]{varA});
    }
    Expr genAxiom5(Expr a) {
        return gen(canonicAxiom5, new Expr[]{a}, new Var[]{varA});
    }
    Expr genAxiom6(Expr a, Expr b) {
        return gen(canonicAxiom6, new Expr[]{a, b}, new Var[]{varA, varB});
    }
//    Expr genAxiom6(Expr a, Expr b) {
//        if (!s.containsKey(a.toString() + b.toString())) {
//            s.put(a.toString() + b.toString(), gen(canonicAxiom6, new Expr[]{a, b}, new Var[]{varA, varB}));
//        }
//        return s.get(a.toString() + b.toString());
//    }

}
