import java.util.HashMap;

public class Variable extends Expression {
    final int VAR = 2;
    String name;

    Variable(String name) {
        this.name = name;
        instance = VAR;
        int res = 0;
        for (int i = 0; i < name.length(); i++) {
            res = res * HASHP + name.charAt(i);
        }
        cachedHashCode = res;
    }

    public String toString() {
        return name;
    }

    public boolean equalStruct(Expression o, HashMap<String, String> d, boolean type) {
        if (o == null) {
            return false;
        }
        if (type) {
            if (d.containsKey(name)) {
                return d.get(name).equals(o.toString());
            } else {
                d.put(name, o.toString());
                return true;
            }
        } else {
            return instance == o.instance && ((Variable) o).name.equals(name);
        }
    }
}
