import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Lenovo on 03.01.2017.
 */
public class Main {
    static PrintWriter out;

    static private Var zero = new Var("0");
    static private Var z1 = new Var("a");
    static private Var z2 = new Var("b");
    static private Var z3 = new Var("c");

    public static void main(String[] args) throws FileNotFoundException {
        out = new PrintWriter(new File("output.txt"));
        ProofGenerator gen = new ProofGenerator(out);
//        gen.aa(new BiFunc(new BiFunc(z1, zero, "+"), z1, "="));
        gen.lemmaG(z1, z2);
//        gen.genAxiom6(zero, z1);
//        gen.lemmaE(z1, z2, z3);
//        gen.aa(ProofGenerator.plus(ProofGenerator.varA, ProofGenerator.varB));
//        gen.mPapbPsEQVapbs(new Var("a"), new Var("b"));
//        Numb zero = new Numb(0, new Var("0"));
//        Numb one = new Numb(1, zero);
//        Numb two = new Numb(1, one);
//        Numb three = new Numb(1, two);
//        gen.abba(new Var("t"), new BiFunc(new Var("t"), new Var("0"), "+"));
//        gen.lemmaF(new Var("t"));
//        gen.aa(new Var("a"));
//        gen.notALessOrEqualB(2, 1);
//        gen.commutativity();
//        gen.aLessOrEqualB(0, 1, ProofGenerator.LESS);
//        gen.aLessOrEqualB(0, 2, ProofGenerator.LESS);
//        gen.aLessOrEqualB(1, 2, ProofGenerator.LESS);
//        gen.notALessOrEqualB(2, 1);
//        gen.foo(4, 2);
//        int n = 3;
//        for (int a = 0; a < n; a++) {
//            for (int b = a; b < n; b++) {
//                gen.aLessB(a, b);
//            }
//        }
//        aLessB(0, 1);
        out.close();
    }

}
