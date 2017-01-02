import java.util.HashMap;

public class Variable extends Expression {
    final static int VAR = 2;

    Variable(String cachedToString) {
//        System.out.println("Var(" + cachedToString + ")");
        this.cachedToString = cachedToString;
        instance = VAR;
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        System.out.println("Variable.equalStruct");
        System.out.println("This shouldn't be called");
        System.exit(1);
        return false;
    }

}
