import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Lenovo on 11.12.2016.
 */
public class Main {

    static BufferedReader in;
    static PrintWriter out;
    static Expression alpha = null;
    static Parser p = new Parser();
    static HashMap<Expression, Integer> proved = new HashMap<>();
    static ArrayList<Expression> provedList = new ArrayList<>();
    static HashSet<String> hypoes = new HashSet<>();
    static Multiset<String> substitutionHelperSetForFreeVars;
    private static ArrayList<Expression> axioms = new ArrayList<Expression>() {{
        try {
            BufferedReader axiomsReader = new BufferedReader(new FileReader("Axioms.txt"));
            String axiom;
            while ((axiom = axiomsReader.readLine()) != null) {
                add(p.parse(axiom).getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }};
    private static Expression d;

    static void Debug() {
//        Expression expr = p.parse("P(f(a),g(b))->?b1P(f(a),g(b1))").getKey();
//        System.out.println(isExistAxiom(expr));
//        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new FileReader("input.txt"));
        out = new PrintWriter(new File("output.txt"));
        Debug();
        parseHyppothethis();
        String exprS;
        int exprId;
        for (exprS = in.readLine(), exprId = 0; exprS != null; exprS = in.readLine(), ++exprId) {
            if (exprId % 100 == 0)
                System.out.println(exprId);
//            System.out.println(exprS);
            Expression expr = p.parse(exprS).getKey();
            Pair<Integer, Integer> indexes;
            Integer index;
            proved.put(expr, exprId);
            provedList.add(expr);
            if (expr.equals(alpha)) {
                out.println(alpha + "->" + alpha + "->" + alpha);
                out.println("(" + alpha + "->" + alpha + "->" + alpha + ")->" +
                        "(" + alpha + "->(" + alpha + "->" + alpha + ")->" + alpha + ")->" +
                        "(" + alpha + "->" + alpha + ")");
                out.println("(" + alpha + "->(" + alpha + "->" + alpha + ")->" + alpha + ")");

                out.println("(" + alpha + "->(" + alpha + "->" + alpha + ")->" + alpha + ")->" +
                        "(" + alpha + "->" + alpha + ")");
                out.println("(" + alpha + "->" + alpha + ")");
            } else if (isAxiom(expr)
                    || isForAllAxiom(expr)
                    || isExistAxiom(expr)
                    || isHypo(expr)) {
                if (alpha == null) {
                    out.println(expr);
                    continue;
                }

                out.println(expr);
                out.println(expr + "->" + alpha + "->" + expr);
                out.println(alpha + "->" + expr);
            } else if ((indexes = isMP(expr)) != null) {
                if (alpha == null) {
                    out.println(expr);
                    continue;
                }

                out.println("(" + alpha + "->" + provedList.get(indexes.getKey()) + ")->"
                        + "(" + alpha + "->" + provedList.get(indexes.getValue()) + ")->"
                        + "(" + alpha + "->" + expr + ")");

                out.println("(" + alpha + "->" + provedList.get(indexes.getValue()) + ")->"
                        + "(" + alpha + "->" + expr + ")");

                out.println("(" + alpha + "->" + expr + ")");
            } else if ((index = isForAllRule(expr)) != null) {
                if (alpha == null) {
                    out.println(expr);
                    continue;
                }

                Expression xPsi = expr.children.get(1);
                Expression phi = provedList.get(index).children.get(0);
                Expression psi = provedList.get(index).children.get(1);

                additionalLemma("abc_AandBC", alpha, phi, psi);
                out.println(alpha + "&" + phi + "->" + psi);
                out.println(alpha + "&" + phi + "->" + xPsi);
                additionalLemma("AandBC_abc", alpha, phi, xPsi);
                out.println(alpha + "->" + phi + "->" + xPsi);
            } else if ((index = isExistRule(expr)) != null) {
                if (alpha == null) {
                    out.println(expr);
                    continue;
                }

                Expression xPsi = expr.children.get(0);
                Expression psi = provedList.get(index).children.get(0);
                Expression phi = provedList.get(index).children.get(1);

                additionalLemma("abc_bac", alpha, psi, phi);
                out.println(psi + "->" + alpha + "->" + phi);
                out.println(xPsi + "->" + alpha + "->" + phi);
                additionalLemma("abc_bac", xPsi, alpha, phi);
                out.println(alpha + "->" + xPsi + "->" + phi);
            } else {
                out.println("Вывод некорректен начиная с формулы номер " + exprId);
//                out.println(expr);
                out.close();
                return;
            }
        }
        System.out.println("File is correct");

        out.close();
    }

    private static Integer isExistRule(Expression expr) {
        if (expr.instance == '>') {
            Expression existPsi = expr.children.get(0);
            Expression phi = expr.children.get(1);
            if (existPsi.instance == '?') {
                Expression var = existPsi.children.get(0);
                Expression psi = existPsi.children.get(1);
                Multiset<String> freeVars = HashMultiset.create(), nonFreeVars = HashMultiset.create();
                getFreeVars(phi, freeVars, nonFreeVars);
                if (!freeVars.contains(var.toString()) && proved.containsKey(new BinaryOperation(psi, phi, BinaryOperation.Operation.CON))) {
                    return proved.get(new BinaryOperation(psi, phi, BinaryOperation.Operation.CON));
                }
            }
        }
        return null;
    }

    private static Integer isForAllRule(Expression expr) {
        if (expr.instance == '>') {
            Expression phi = expr.children.get(0);
            Expression forAllPsi = expr.children.get(1);
            if (forAllPsi.instance == '@') {
                Expression var = forAllPsi.children.get(0);
                Expression psi = forAllPsi.children.get(1);
//                System.out.println(var + " " + phi + " " + psi);
                Multiset<String> freeVars = HashMultiset.create(), nonFreeVars = HashMultiset.create();
                getFreeVars(phi, freeVars, nonFreeVars);
//                System.out.println(freeVars);
                if (!freeVars.contains(var.toString()) && proved.containsKey(new BinaryOperation(phi, psi, BinaryOperation.Operation.CON))) {
                    return proved.get(new BinaryOperation(phi, psi, BinaryOperation.Operation.CON));
                }
            }
        }
        return null;
    }

    private static void additionalLemma(String abc_aandBC, Expression A, Expression B, Expression C) throws IOException {
        BufferedReader lemmaReader = new BufferedReader(new FileReader(abc_aandBC));
        for (String in = lemmaReader.readLine(); in != null; in = lemmaReader.readLine()) {
            for (int i = 0; i < in.length(); i++) {
                String d;
                switch (in.charAt(i)) {
                    case 'A':
                        out.print(A);
                        break;
                    case 'B':
                        out.print(B);
                        break;
                    case 'C':
                        out.print(C);
                        break;
                    default:
                        out.print(in.charAt(i));
                }
            }
            out.println();
        }

    }

    private static boolean isAxiom(Expression expr) {
        for (Expression axiom : axioms) {
            if (axiom.equalStruct(expr, new HashMap<>()))
                return true;
        }
        return isInduction(expr);
    }

    static void parseHyppothethis() throws IOException {
        String d = in.readLine();
        int indexOfHypEnd = d.indexOf("|-");
        if (indexOfHypEnd == -1) {
            out.println(d);
        } else {
            String l = d.substring(0, indexOfHypEnd) + ",";
            String r = d.substring(indexOfHypEnd + 2, d.length());
            boolean flag = false;
            while (true) {
                Pair<Expression, Integer> res = p.parse(l);
                l = l.substring(res.getValue() + 1);
                alpha = res.getKey();
                if (l.isEmpty())
                    break;
                if (flag)
                    out.print(",");
                out.print(res.getKey());
                hypoes.add(res.getKey().toString());
                flag = true;
            }
            out.println("|-" + alpha + "->" + r);
        }
    }

    static boolean isHypo(Expression expr) {
        return hypoes.contains(expr.toString());
    }

    static Pair<Integer, Integer> isMP(Expression expr) {
        for (Expression s : proved.keySet()) {
            if (s.instance == '>' && s.children.get(1).equals(expr)) {
                if (proved.containsKey(s.children.get(0))) {
                    return new Pair<>(proved.get(s.children.get(0)), proved.get(s));
                }
            }
        }
        return null;
    }

    private static boolean isExistAxiom(Expression expr) {
//        System.out.println(expr);
        if (expr.instance == '>') {
            Expression lhs = expr.children.get(0); // Phi[x:=theta]
            Expression rhs = expr.children.get(1); // ?x Phi
            if (rhs.instance == '?') {
                Variable x = ((Quantor) rhs).var; // x
                Expression phi = ((Quantor) rhs).cur; // Phi
//                System.out.println(x);
//                System.out.println("?" + x + "Phi=" + rhs);
//                System.out.println("Phi[x := theta]=" + lhs);

                return isRightSubstitutionMade(phi, lhs, x, null);
            }
        }
        return false;
    }

    private static boolean isForAllAxiom(Expression expr) {
//        System.out.println((char)expr.instance);
        if (expr.instance == '>') {
            Expression lhs = expr.children.get(0); // @x Phi
            Expression rhs = expr.children.get(1); // Phi[x := theta]
            if (lhs.instance == '@') {
//                System.out.println("Got here");
                Variable x = ((Quantor) lhs).var; // x
                Expression phi = ((Quantor) lhs).cur; // Phi
//                System.out.println(phi + " " + x + " " + rhs);
                return isRightSubstitutionMade(phi, rhs, x, null);
            }
        }
        return false;
    }

    private static boolean isRightSubstitutionMade(Expression phi, Expression rhs, Variable x, Expression dvalue) {
        d = dvalue;
        substitutionHelperSetForFreeVars = HashMultiset.create();
//        System.out.println(d + " " + substitutionHelperSetForFreeVars.isEmpty());
        return isRightSubstitutionMadeHelper(phi, rhs, x, HashMultiset.create());
    }

    private static boolean isRightSubstitutionMadeHelper(Expression phi, Expression rhs, Variable x, Multiset<String> nonFreeVars) {
//        System.out.println(phi + "|" + rhs);
//        System.out.println(phi.instance + " " + rhs.instance + " " + Variable.VAR + "\n");
        if (phi.equals(x) && !nonFreeVars.contains(phi.toString())) {
            if (d == null) {
                getFreeVars(rhs, substitutionHelperSetForFreeVars, HashMultiset.create());
                d = rhs;
            } else if (!rhs.equals(d)) {
//                System.out.println("False here");
                return false;
            }
            for (String shouldBeFree : substitutionHelperSetForFreeVars) {
                if (nonFreeVars.contains(shouldBeFree))
                    return false;
            }
            return true;
        }

        if (phi.instance != rhs.instance)
            return false;
        if (phi.instance == Variable.VAR && !phi.equals(rhs))
            return false;
        if (phi.instance == Predicat.PREDICAT && !((Predicat) phi).pred.equals(((Predicat) rhs).pred))
            return false;
        if (phi.instance == FuncSymbol.FUNCSYMBOL && !((FuncSymbol) phi).var.equals(((FuncSymbol) rhs).var))
            return false;

        if (phi.instance == '@' || phi.instance == '?') {
            nonFreeVars.add(((Quantor) phi).var.toString());
        }

        assert phi.children.size() == rhs.children.size();
        for (int i = 0; i < phi.children.size(); i++) {
            if (!isRightSubstitutionMadeHelper(phi.children.get(i), rhs.children.get(i), x, nonFreeVars))
                return false;
        }

        if (phi.instance == '@' || phi.instance == '?') {
            nonFreeVars.remove(((Quantor) phi).var.toString(), 1);
        }
        return true;
    }


    static void getFreeVars(Expression phi, Multiset<String> freeVars, Multiset<String> nonFreeVars) {
        switch (phi.instance) {
            case '>':
            case '&':
            case '|':
            case '*':
            case '+':
            case '=':
            case Negate.NEGATE:
            case Predicat.PREDICAT:
                for (Expression child : phi.children) {
                    getFreeVars(child, freeVars, nonFreeVars);
                }
                break;
            case '@':
            case '?':
                nonFreeVars.add(((Quantor) phi).var.toString());
                getFreeVars(((Quantor) phi).cur, freeVars, nonFreeVars);
                nonFreeVars.remove(((Quantor) phi).var.toString(), 1);
                break;
            case Variable.VAR:
                if (!nonFreeVars.contains(phi.toString()))
                    freeVars.add(phi.toString());
            default:
                break;
        }
    }


    static boolean isInduction(Expression expr) {
        if (expr.instance == '>') {
            Expression psi = expr.children.get(1);
            Expression lhs = expr.children.get(0);
            if (lhs.instance == '&') {
                Expression psi0 = lhs.children.get(0);
                Expression rhs = lhs.children.get(1);
                if (rhs.instance == '@') {
                    Variable var = (Variable) rhs.children.get(0);
                    Expression conj = rhs.children.get(1);
                    if (conj.instance == '>' && psi.equals(conj.children.get(0))) {
                        Expression psix = conj.children.get(1);


                        return isRightSubstitutionMade(psi, psi0, var, new Zero())
                                && isRightSubstitutionMade(psi, psix, var, new AddOne(var));
                    }
                }
            }
        }
        return false;
    }


}
