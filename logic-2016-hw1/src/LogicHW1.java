import jdk.internal.util.xml.impl.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogicHW1 {
    final static String fileName = "D:\\logic-2016\\logic-2016-hw1\\HW1\\tmp.in";
    static Parser p = new Parser();
    static Map<Expression, Integer> data;
    static Map<Expression, ArrayList<Pair>> conRightParts;
    static HashMap<Expression, Integer> assumption;
    static ArrayList<Expression> expressions;
    static PrintWriter out;
    static BufferedReader in;
    private static ArrayList<Expression> axioms = new ArrayList<Expression>() {{
        add(p.parse("A->B->A"));
        add(p.parse("(A->B)->(A->B->C)->(A->C)"));
        add(p.parse("A->B->A&B)"));
        add(p.parse("A&B->A"));
        add(p.parse("A&B->B"));
        add(p.parse("A->A|B"));
        add(p.parse("B->A|B"));
        add(p.parse("(A->C)->(B->C)->(A|B->C)"));
        add(p.parse("(A->B)->(A->!B)->!A"));
        add(p.parse("!!A->A"));
    }};

    static boolean isAxiom(Expression d) {
//        System.out.println("Checking " + d);
        for (int i = 0; i < axioms.size(); i++) {
            if (axioms.get(i).equalStruct(d, new HashMap<>(), true)) {
                out.println("Сх. акс. " + (i + 1));
                return true;
            }
        }
        return false;
    }

    static private boolean modusPonens(Expression b) {
/*
        for (int i = 0; i < expressions.size(); i++) {
            Expression cur = expressions.get(i);
            if (cur.instance == BinaryOperation.BINARYOPERATION) {
                BinaryOperation spec = (BinaryOperation) cur;
                if (spec.op == BinaryOperation.Operation.CON) {
                    if (data.containsKey(spec.lhs) && spec.rhs.hashCode() == b.hashCode()) {
                        out.println("M.P. " + data.get(spec.lhs) + ", " + i + 1);
                        return true;
                    }
                }
            }
        }
*/
        if (conRightParts.containsKey(b)) {
            ArrayList<Pair> arr = conRightParts.get(b);
            for (Pair pair : arr) {
                if (data.containsKey(pair.d)) {
                    out.println("M.P. " + data.get(pair.d) + ", " + pair.id);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAssumption(Expression d) {
        if (assumption.containsKey(d)) {
            out.println(assumption.get(d) + 1);
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new FileReader(fileName));
        out = new PrintWriter(new File("output.txt"));
        data = new HashMap<>();
        assumption = new HashMap<>();
        conRightParts = new HashMap<>();
        expressions = new ArrayList<>();

        int ind = 0;
//        String title = in.readLine();
//        StringTokenizer t = new StringTokenizer(title);
//        while (t.hasMoreTokens()) {
//            title = t.nextToken();
//            if (title.equals("|-")) {
//                break;
//            }
//            Expression d = p.parse(title);
//            assumption.put(d, ind);
//            ind++;
//        }

        ind = 0;
        for (String cur = in.readLine(); cur != null; cur = in.readLine()) {
//            System.out.println(ind);
//            System.out.println(cur);
            Expression d = p.parse(cur);
            if (!isAxiom(d) && !isAssumption(d) && !modusPonens(d)) {
                out.println("Не доказано");
            }
            ind++;

            expressions.add(d);
            data.put(d, ind);
            if (d.instance == BinaryOperation.BINARYOPERATION) {
                BinaryOperation bd = (BinaryOperation) d;
                if (bd.op == BinaryOperation.Operation.CON) {
                    if (!conRightParts.containsKey(bd.rhs)) {
                        conRightParts.put(bd.rhs, new ArrayList<>());
                    }
                    conRightParts.get(bd.rhs).add(new Pair(bd.lhs, ind));
                }
            }
            if (ind % 1000 == 0)
                System.out.println(ind);
        }


        out.close();
    }


    static class Pair {
        Expression d;
        int id;
        Pair(Expression d, int id) {
            this.d = d;
            this.id = id;
        }
    }
}
