import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class LogicHW1 {
    //    static String fileName = "There's probadly no such file name";
    static String fileName = "HW1//tmp.in";
    static Parser p = new Parser();
    static Map<String, Integer> data;
    static Map<String, ArrayList<Pair>> conRightParts;
    static Map<String, Integer> assumption;
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
                out.println("Сх. акс. " + (i + 1) + ")");
                return true;
            }
        }
        return false;
    }

    static private boolean modusPonens(String b) {
        if (conRightParts.containsKey(b)) {
            ArrayList<Pair> arr = conRightParts.get(b);
            for (int i = 0; i < arr.size(); i++) {
                Pair pair = arr.get(i);
                if (data.containsKey(pair.d)) {
                    if (i != 0) {
                        arr.set(0, pair);
                        arr.ensureCapacity(1);
                    }
                    out.println("M.P. " + data.get(pair.d) + ", " + pair.id + ")");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAssumption(String d) {
        if (assumption.containsKey(d)) {
            out.println("Предп. " + assumption.get(d) + ")");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            if (args.length > 0) fileName = args[0];
            long time = System.currentTimeMillis();

            in = new BufferedReader(new FileReader(fileName));
            out = new PrintWriter(new File("output.txt"));
            data = new HashMap<>();
            assumption = new HashMap<>();
            conRightParts = new HashMap<>();

            int ind = 0;
            String title = in.readLine();
            out.println(title);
            StringTokenizer t = new StringTokenizer(title);
            while (t.hasMoreTokens()) {
                title = t.nextToken();
                if (title.equals("|-")) {
                    break;
                }
                ind++;
                Expression d = p.parse(title);
                assumption.put(d.toString(), ind);
            }

            ind = 0;
            for (String cur = in.readLine(); cur != null; cur = in.readLine()) {
                Expression d = p.parse(cur);
                out.print("(" + (ind + 1) + ") " + cur + " (");
                if (!isAxiom(d) && !isAssumption(d.toString()) && !modusPonens(d.toString())) {
                    out.println("Не доказано)");
                }
                ind++;
                data.put(d.toString(), ind);
                if (d.instance == BinaryOperation.BINARYOPERATION) {
                    BinaryOperation bd = (BinaryOperation) d;
                    if (bd.op == BinaryOperation.Operation.CON) {
                        if (!conRightParts.containsKey(bd.rhs.toString())) {
                            conRightParts.put(bd.rhs.toString(), new ArrayList<>());
                        }
                        conRightParts.get(bd.rhs.toString()).add(new Pair(bd.lhs.toString(), ind));
                    }
                }
            }
            out.close();
            System.out.println("Time= " + (System.currentTimeMillis() - time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class Pair {
        String d;
        int id;

        Pair(String d, int id) {
            this.d = d;
            this.id = id;
        }
    }
}
