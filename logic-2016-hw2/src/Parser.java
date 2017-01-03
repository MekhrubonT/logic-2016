import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;


public class Parser {
    String expression;
    int pointer;
    BinaryOperation.Operation FIRSTOPERATIONS[] =
            new BinaryOperation.Operation[]{BinaryOperation.Operation.CON,
                    BinaryOperation.Operation.OR, BinaryOperation.Operation.AND};

    Pair<Expression, Integer> parse(String exp) {
        this.expression = exp;
        pointer = 0;
        return new Pair<>(binaryOperations(0), pointer);
    }

    void skip() {
        while (pointer < expression.length() && Character.isWhitespace(expression.charAt(pointer))) {
            pointer++;
        }
    }

    boolean currentOperationIs(char c) {
        if (expression.charAt(pointer) == '-')
            pointer++;
        return expression.charAt(pointer) == c;
    }

    Expression binaryOperations(int pos) {
        if (pos == FIRSTOPERATIONS.length) {
            return unaryOperations();
        }
        ArrayList<Expression> list = new ArrayList<Expression>() {{
            add(binaryOperations(pos + 1));
        }};
        while (pointer < expression.length()) {
            skip();
            if (pointer == expression.length() || !currentOperationIs(FIRSTOPERATIONS[pos].toChar())) {
                break;
            }
            pointer++;
            list.add(binaryOperations(pos + 1));
        }


        if (FIRSTOPERATIONS[pos] == BinaryOperation.Operation.CON) {
            Collections.reverse(list);
            return list.stream().skip(1).reduce(list.get(0), (a, b) -> new BinaryOperation(b, a, FIRSTOPERATIONS[pos]));
        } else {
            return list.stream().skip(1).reduce(list.get(0), (a, b) -> new BinaryOperation(a, b, FIRSTOPERATIONS[pos]));
        }
    }

    Expression unaryOperations() {
//        System.out.println("Parser.unaryOperations " + pointer);
        switch (expression.charAt(pointer)) {
            case '(':
//             /**/   System.out.println(pointer);
                int level = 1, cp = pointer + 1, isExpression = 0;
                while (level != 0 && isExpression == 0) {
                    switch (expression.charAt(cp)) {
                        case '(':
                            ++level;
                            break;
                        case ')':
                            --level;
                            break;
                        case '>':
                        case '&':
                        case '|':
                        case '!':
                        case '@':
                        case '?':
                        case '=':
                            isExpression = 1;
                            break;
                        default:
                            if (Character.isUpperCase(expression.charAt(cp)))
                                isExpression = 1;
                    }
                    ++cp;
                }
//                System.out.println("it's expression? " + isExpression);
                if (isExpression == 1) {
                    pointer++;
//                    System.out.println("\t" + pointer);
                    Expression result = binaryOperations(0);
                    assert expression.charAt(pointer) == ')';
                    pointer++;
                    return result;
                } else {
                    return predicat();
                }
            case '!':
                pointer++;
                return new Negate(unaryOperations());
            case '@':
            case '?':
                pointer++;
                return new Quantor(expression.charAt(pointer - 1), variable(), unaryOperations());
            default:
                return predicat();
        }
    }

    String getNeededText(Predicate<Character> p) {
        int spointer = pointer;
        assert p != null && pointer < expression.length();
        assert p.test(expression.charAt(pointer));
        ++pointer;
        while (pointer < expression.length() && Character.isDigit(expression.charAt(pointer)))
            ++pointer;
        return expression.substring(spointer, pointer);
    }

    Variable variable() {
        return new Variable(getNeededText(Character::isLowerCase));
    }


    Expression predicat() {
        if (Character.isUpperCase(expression.charAt(pointer))) {
            String var = getNeededText(Character::isUpperCase);
            ArrayList<Expression> args = new ArrayList<>();
//            System.out.println("Here\n");
            if (pointer < expression.length() && expression.charAt(pointer) == '(') {
                do {
                    ++pointer;
                    args.add(sumand());
                } while (expression.charAt(pointer) == ',');
                assert expression.charAt(pointer) == ')';
                ++pointer;
            }

            return new Predicat(var, args);
        } else {
            Expression t1 = sumand();
//            System.out.println("t1=" + t1);
//            System.out.println(pointer + " " + expression.length() + " " + expression.charAt(pointer));
            if (pointer < expression.length() && expression.charAt(pointer) == '=') {
                assert expression.charAt(pointer) == '=';
                ++pointer;
                return new BinaryOperation(t1, sumand(), BinaryOperation.Operation.EQV);
            } else {
                return t1;
            }
        }
    }

    Expression sumand() {
        Expression l = multiply();
        while (pointer < expression.length() && expression.charAt(pointer) == '+') {
            ++pointer;
            l = new BinaryOperation(l, multiply(), BinaryOperation.Operation.PLUS);
        }
        return l;
    }

    Expression multiply() {
        Expression l = iDoNotKnowHowToNameThisFunction();
        while (pointer < expression.length() && expression.charAt(pointer) == '*') {
            ++pointer;
            l = new BinaryOperation(l, iDoNotKnowHowToNameThisFunction(), BinaryOperation.Operation.MULT);
        }
        return l;
    }

    Expression iDoNotKnowHowToNameThisFunction() {
        Expression res;
//        System.out.println("Parser.iDoNotKnowHowToNameThisFunction " + pointer);
        switch (expression.charAt(pointer)) {
            case '0':
                ++pointer;
                res = new Zero();
                break;
            case '(':
                ++pointer;
//                System.out.println("Parser.iDoNotKnowHowToNameThisFunction");

                res = sumand();
//                System.out.println(res);
//                System.out.println(pointer + " " + expression.length() + " " + expression.charAt(pointer));
                assert expression.charAt(pointer) == ')';
                ++pointer;
                break;
            default:
                String text = getNeededText(Character::isLowerCase);
//                System.out.println("\t" + text);
                if (pointer < expression.length() && expression.charAt(pointer) == '(') {
//                    System.out.println("got here");
                    ArrayList<Expression> args = new ArrayList<>();
                    do {
                        ++pointer;
                        args.add(sumand());
                    } while (expression.charAt(pointer) == ',');
                    assert expression.charAt(pointer) == ')';
                    pointer++;
                    res = new FuncSymbol(text, args);
                } else {
                    res = new Variable(text);
                }

        }
        while (pointer < expression.length() && expression.charAt(pointer) == '\'') {
            res = new AddOne(res);
            ++pointer;
        }
        return res;
    }

}
