import java.util.HashMap;

public abstract class Expression {
    final int HASHP = 1873;
    final int HASHP100 = 989977729;
    protected int cachedHashCode;
    int instance;

    abstract public boolean equalStruct(Expression ot, HashMap<String, String> d, boolean type);

    @Override
    abstract public String toString();

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof Expression)) && equalStruct((Expression) o, new HashMap<>(), false);
    }

}
