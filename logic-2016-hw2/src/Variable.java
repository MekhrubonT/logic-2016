import java.util.HashMap;

public class Variable extends Expression {
    final static int VAR = 2;

    Variable(String cachedToString) {
        this.cachedToString = cachedToString;
        instance = VAR;
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        return equals(o);
//        if (o == null) {
//            return false;
//        }
//        if (d.containsKey(cachedToString)) {
//            return d.get(cachedToString).equals(o.toString());
//        } else {
//            d.put(cachedToString, o.toString());
//            return true;
//        }
    }

}
