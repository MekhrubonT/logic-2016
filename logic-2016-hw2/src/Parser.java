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
        switch (expression.charAt(pointer)) {
            case '(':
                pointer++;
                Expression result = binaryOperations(0);
                skip();
                pointer++;
                return result;
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
            assert expression.charAt(pointer) == '(';
            do {
                ++pointer;
                args.add(sumand());
            } while (expression.charAt(pointer) == ',');
            assert expression.charAt(pointer) == ')';
            ++pointer;
            Predicat predicat = new Predicat(var, args);
            return predicat;
        } else {
            Expression t1 = sumand();
            assert expression.charAt(pointer) == '=';
            ++pointer;
            return new BinaryOperation(t1, sumand(), BinaryOperation.Operation.EQV);
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
        switch (expression.charAt(pointer)) {
            case '0' : res = new Zero(); break;
            case '(' : ++pointer;
                        res = sumand();
                        assert expression.charAt(pointer) == ')';
                        ++pointer;
                break;
            default:
                String text = getNeededText(Character::isLowerCase);
                if (pointer < expression.length() && expression.charAt(pointer) == '(') {
                    ArrayList<Expression> args = new ArrayList<>();
                    do {
                        args.add(sumand());
                    } while (expression.charAt(pointer) == ',');
                    assert expression.charAt(pointer) == ')';
                    pointer++;
                    res = new Variable(text);
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
