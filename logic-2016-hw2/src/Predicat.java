import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lenovo on 26.12.2016.
 */
public class Predicat extends Expression {
    final static int PREDICAT = 3;
    final String pred;
//    final ArrayList<Expression> arguments;

    public Predicat(String pred, ArrayList<Expression> arguments) {
        this.pred = pred;
        children = arguments;
        instance = PREDICAT;
        StringBuilder res = new StringBuilder();
        res.append(pred).append("(");
        for (int i = 0; i < arguments.size(); i++) {
            res.append(arguments.get(i));
            if (i != arguments.size() - 1)
                res.append(",");
        }
        cachedToString = res.append(")").toString();
    }

    @Override
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
