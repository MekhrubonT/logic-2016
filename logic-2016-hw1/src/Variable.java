import java.util.HashMap;

/**
 * Created by Lenovo on 13.10.2016.
 */
public class Variable extends Expression {
    String name;
    final int VAR = 2;
    Variable(String name) {
        this.name = name;
        instance = VAR;
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
