import java.util.HashMap;

/**
 * Created by Lenovo on 26.12.2016.
 */
public class Zero extends Expression {
    final int ZERO = 4;

    @Override
    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        System.out.println("Variable.equalStruct");
        System.out.println("This shouldn't be called");
        System.exit(0);
        return false;
    }

    Zero() {
        cachedToString = "0";
        instance = ZERO;
    }
}
