import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lenovo on 03.01.2017.
 */
public class FuncSymbol extends Expression {
    static int FUNCSYMBOL = -1;
    final String var;

    public FuncSymbol(String text, ArrayList<Expression> args) {
        var = text;
        children = args;
        instance = FUNCSYMBOL;
        StringBuilder res = new StringBuilder().append(var);
        if (!args.isEmpty()) {
            res.append("(");
            for (int i = 0; i < args.size(); i++) {
                res.append(args.get(i));
                if (i != args.size() - 1)
                    res.append(",");
            }
            res = res.append(")");
        }
        cachedToString = res.toString();
    }

    @Override
    public boolean equalStruct(Expression o, HashMap<String, String> d) {
        return equals(o);
    }
}
