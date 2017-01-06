//import deduction.Deduction;

import java.io.*;

import static helper.Helper.*;

/**
 * Created by Lenovo on 03.01.2017.
 */
public class Main {

    static PrintWriter out;


    public static void main(String[] args) throws IOException {
        out = new PrintWriter(new File("output.txt"));
        ProofGenerator gen = new ProofGenerator(out);
        gen.genABBA(varA, varB);
        gen.aLessOrEqualB(99, 100, true);
//        gen.nless(10, 5);
        out.close();
    }

}
