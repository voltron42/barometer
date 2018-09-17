package el2;

import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Decoder {

    private final ExpressionFactory factory;

    private final SimpleContext context;

    public Decoder() {
        factory = ExpressionFactory.newInstance();
        context = new SimpleContext();
    }

    public Instance decode(String expression) {
        ValueExpression ve = factory.createValueExpression(context, expression, Object.class);
        return Instance.create(ve.getValue(context));
    }

    public void addFunction(String prefix, String label, Method method) {
        context.setFunction(prefix, label, method);
    }

    public void set(String label, Instance inst) {
        Object value = getValue(inst);
        context.setVariable(label, factory.createValueExpression(value, value.getClass()));
    }

    public void remove(String label) {
        context.setVariable(label, null);
    }

    private Object getValue(Instance inst) {
        switch (inst.getType()) {
            case MAP:
                Map<Object,Object> map = new HashMap<>();
                for (Map.Entry<String, Instance> entry : inst.mapValue().entrySet()) {
                    entry.getValue().put(map,entry.getKey());
                }
                return map;
            case DATE:
                return inst.dateValue();
            case FILE:
                return inst.filePathValue();
            case LIST:
                List<Object> list = new ArrayList<>();
                for (Instance child : inst.listValue()) {
                    child.append(list);
                }
                return list;
            case LONG:
                return inst.integerValue();
            case TEXT:
                return inst.stringValue();
            case DOUBLE:
                return inst.decimalValue();
            case BOOLEAN:
                return inst.booleanValue();
            default:
                throw new IllegalArgumentException();
        }
    }

}
