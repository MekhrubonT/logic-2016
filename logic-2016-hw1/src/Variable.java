import java.util.HashMap;

public class Variable extends Expression {
    final int VAR = 2;

    Variable(String cachedToString) {
        this.cachedToString = cachedToString;
        instance = VAR;

    }

    public String toString() {
        return cachedToString;
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        if (o == null) {
            return false;
        }
        if (d.containsKey(cachedToString)) {
            return d.get(cachedToString).equals(o.toString());
        } else {
            d.put(cachedToString, o.toString());
            return true;
        }
    }
}
