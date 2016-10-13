import java.util.HashMap;

/**
 * Created by Lenovo on 13.10.2016.
 */

public abstract class Expression {
    int instance;
    abstract public boolean equalStruct(Expression ot, HashMap<String, String> d, boolean type);
    abstract public String toString();
}
