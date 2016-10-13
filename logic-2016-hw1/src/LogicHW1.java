import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Lenovo on 16.09.2016.
 */
public class LogicHW1 {

    static Parser p = new Parser();
    HashMap<Expression, Integer> data = new HashMap<>();
    private static ArrayList<Expression> axioms = new ArrayList<Expression>(){{
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

    static int isAxiom(String exp) {
        Expression d = p.parse(exp);
        for (int i = 0; i < axioms.size(); i++) {
            if (axioms.get(i).equalStruct(d, new HashMap<>(), true))
                return i;
        }
        return -1;
    }

    static ArrayList<Expression> expressions;

    static private boolean modusPonuns(Expression b) {
        for (int i = 0; i < expressions.size(); i++) {
            Expression cur = expressions.get(i);
            if (cur.instance == BinaryOperation.BINARYOPERATION) {

            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(axioms);
//        System.out.println(isAxiom("A&B->A"));
        System.out.println(isAxiom("(A&B)&C->(A&B)"));

    }
/*
    public static ArrayList<String> axioms = new ArrayList<String>() {{
        add("A->B->A");
    }};
    static String axiom, statement;
    static HashMap<Character, String> propositions;

    public static void deletePair(Character prep, String metaP) {
        propositions.remove(prep, metaP);
    }

    static Character prepositions[] = new Character[]{'A', 'B', 'C'};
    static Character operations[] = new Character[]{'-', '>', '!', '&', '!'};
    static Character brackets[] = new Character[]{'(', ')'};



    public static boolean contains(Character arr[], Character ob) {
        for (Character cur: arr) {
            if (cur.equals(ob)) {
                return true;
            }
        }
        return false;
    }

    public static int balance(int x, Character y) {
        if (y.equals('(')) return x - 1;
        return y.equals(')') ? x + 1 : x;
    }

    public static int checkOrAddNewPreposition(Character prep, String metaP, int bracket) {
//        System.out.println("Preposition " + prep + " " + metaP + " " + bracket);
        if (contains(prepositions, prep) == metaP.isEmpty() || bracket != 0) {
            return 0;
        }
        if (propositions.containsKey(prep)) {
            return propositions.get(prep).equals(metaP) ? 1 : 0;
        }
        propositions.put(prep, metaP);
        return 2;
    }


    public static boolean checkAxiom(int pointerS, int pointerA, int bracket, StringBuilder cur) {
        while (pointerS >= 0 && Character.isWhitespace(statement.charAt(pointerS))) {
            pointerS--;
        }
//        while (pointerA )
        if (bracket < 0) {
            return false;
        }
        if (pointerS < 0 && pointerA < 0) {
            for (Object proposition : propositions.keySet()) {
                System.out.println(proposition + " " + propositions.get(proposition));
            }
            return true;
        } else if (pointerS < 0 || pointerA < 0) {
            return false;
        }
        Character curChar = statement.charAt(pointerS);
//        System.out.println("cur=" + cur.toString());
//        System.out.println(pointerS + " " + pointerA);
//        System.out.println("current charachters\t" + curChar + " " + axiom.charAt(pointerA));
        if (contains(prepositions, axiom.charAt(pointerA))) {
//            System.out.println("Axiom char was variable");
            if (checkAxiom(pointerS - 1, pointerA - 1, balance(bracket, curChar), cur.append(curChar)))
                return true;
            if (checkAxiom(pointerS - 1, pointerA, balance(bracket, curChar), cur))
                return true;
            cur.deleteCharAt(cur.length() - 1);
        } else if (curChar.equals(axiom.charAt(pointerA))) {
//            System.out.println("Axiom char wasn't var");
            int q = checkOrAddNewPreposition(axiom.charAt(pointerA + 1), cur.toString(), bracket);
            if (q > 0 && checkAxiom(pointerS - 1, pointerA - 1, 0, new StringBuilder())) {
                return true;
            }
            if (q == 2) {
                deletePair(axiom.charAt(pointerA), cur.toString());
            }
        }

        return false;
    }

    public static int isAxiom(String statement) {
//        System.out.println(axioms.size());
        for (int i = 0; i < axioms.size(); i++) {
            LogicHW1.statement = statement;
            axiom = axioms.get(i);
            propositions = new HashMap<>();
//            System.out.println("statement=" + statement);
//            System.out.println("axiom=" + axiom);
            if (checkAxiom(statement.length() - 1, axiom.length() - 1, 0, new StringBuilder(""))) {
                return i;
            }
//            System.out.println("FInished cheking");
            break;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println("Result=" + isAxiom("A->B->A"));
//    }
*/


}
