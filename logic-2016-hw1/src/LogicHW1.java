import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogicHW1 {
    final static String fileName = "D:\\logic-2016\\logic-2016-hw1\\HW1\\good6.in";
    static Parser p = new Parser();
    static Map<String, Integer> data;
    static HashMap<String, Integer> assumption;
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
        for (int i = 0; i < axioms.size(); i++) {
            if (axioms.get(i).equalStruct(d, new HashMap<>())) {
                out.println("Сх. акс. " + (i + 1));
                return true;
            }
        }
        return false;
    }

    static private boolean modusPonens(Expression b) {
        for (int i = 0; i < expressions.size(); i++) {
            Expression cur = expressions.get(i);
            if (cur.instance == BinaryOperation.BINARYOPERATION) {
                BinaryOperation spec = (BinaryOperation) cur;
                if (spec.op == BinaryOperation.Operation.CON) {
                    if (data.containsKey(spec.lhs.toString()) && spec.rhs.hashCode() == b.hashCode()) {
                        out.println("M.P. " + data.get(spec.lhs.toString()) + ", " + (i + 1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isAssumption(Expression d) {
        if (assumption.containsKey(d.toString())) {
            out.println(assumption.get(d.toString()) + 1);
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        long time = System.currentTimeMillis();
        in = new BufferedReader(new FileReader(fileName));
        out = new PrintWriter(new File("output.txt"));
        data = new HashMap<>();
        assumption = new HashMap<>();
        expressions = new ArrayList<>();
        int ind = 0;
////        String title = in.readLine();
////        StringTokenizer t = new StringTokenizer(title);
////        while (t.hasMoreTokens()) {
////            title = t.nextToken();
////            if (title.equals("|-")) {
////                break;
////            }
////            Expression d = p.parse(title);
////            assumption.put(d, ind);
////            ind++;
////        }
//
        ind = 0;
        for (String cur = in.readLine(); cur != null; cur = in.readLine()) {
            Expression d = p.parse(cur);
            if (!isAxiom(d) && !isAssumption(d) && !modusPonens(d)) {
                out.println("Не доказано");
            }
            ind++;
            expressions.add(d);
            data.put(d.toString(), ind);
        }
        out.close();
        System.out.println(System.currentTimeMillis() - time);
    }
}
