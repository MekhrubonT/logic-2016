import java.util.HashMap;

/**
 * Created by Lenovo on 26.12.2016.
 */
public class AddOne extends Expression {
    final int ADDONE = 6;
    final Expression cur;

    public AddOne(Expression cur) {
        this.cur = cur;
        cachedToString = cur.cachedToString + "\'";
        instance = ADDONE;
    }

    @Override
    public boolean equalStruct(Expression o, HashMap<String, String> d) {
//        throw new RuntimeException("AddOne.equalStruct");
        return equals(o);
    }
}