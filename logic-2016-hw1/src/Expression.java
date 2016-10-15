import java.util.HashMap;

public abstract class Expression {
    int instance;
    String cachedToString;

    abstract public boolean equalStruct(Expression ot, HashMap<String, String> d);

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
        return !(o == null || !(o instanceof Expression)) && cachedToString.equals(((Expression) o).cachedToString);
    }

}
