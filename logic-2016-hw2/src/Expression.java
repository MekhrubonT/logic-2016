import java.util.ArrayList;
import java.util.HashMap;

public abstract class Expression {
    int instance;
    protected String cachedToString;
    ArrayList<Expression> children = new ArrayList<>();

    @Override
    public String toString() {
        return cachedToString;
    }

    @Override
    public int hashCode() {
        return cachedToString.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Expression))
            return false;
        return toString().equals(o.toString());
    }



    abstract public boolean equalStruct(Expression o, HashMap<String, String> d);

}
