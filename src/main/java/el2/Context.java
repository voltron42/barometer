package el2;

import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;

public class Context {

    private final ExpressionFactory factory;

    private final SimpleContext context;

    private final Stack<Set<String>> scope;

    private final Set<String> global;

    public Context() {
        factory = ExpressionFactory.newInstance();
        context = new SimpleContext();
        global = new HashSet<>();
        scope = new Stack<>();
    }

    public Result<?> decode(String expression) {
        ValueExpression ve = factory.createValueExpression(context, expression, Object.class);
        return new Result(ve.getValue(context));
    }

    public enum ResultType {
        TEXT(String.class),
        BOOLEAN(boolean.class, Boolean.class),
        INTEGER(long.class,Long.class,int.class,Integer.class,short.class,Short.class,byte.class,Byte.class),
        DECIMAL(double.class,Double.class,float.class,Float.class),
        DATETIME(Date.class,java.sql.Date.class,java.sql.Timestamp.class),
        FILEPATH(File.class, Path.class),
        LIST(List.class),
        MAP(Map.class),
        NULL(Void.class, null);
        private Set<Class<?>> classes;

        ResultType(Class<?>... classes) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }

        static ResultType fromObj(Object obj) {
            for (ResultType type : ResultType.values()) {
                for (Class<?> cls : type.classes) {
                    if (cls.isInstance(obj)) {
                        return type;
                    }
                }
            }
            throw new IllegalArgumentException(obj.getClass().getCanonicalName());
        }
    }

    public static class Result<K> {
        private K k;
        private ResultType type;

        public Result(K k) {
            this.k = k;
            this.type = ResultType.fromObj(k);
        }

        public K getK() {
            return k;
        }

        public ResultType getType() {
            return type;
        }
    }

    public void addFunction(String prefix, String label, Method method) {
        context.setFunction(prefix, label, method);
    }

    public void set(String label, Object value) {
        context.setVariable(label, factory.createValueExpression(value, value.getClass()));
        scope.peek().add(label);
    }

    public void setGlobal(String label, Object value) {
        context.setVariable(label, factory.createValueExpression(value, value.getClass()));
        global.add(label);
    }

    public Context rescope() {
        Context out = new Context();
        for(String label : global) {
            out.set(label, decode("${" + label + "}"));
        }
        return out;
    }

    public void scope() {
        scope.push(new HashSet<>());
    }

    public void descope() {
        for (String label : scope.peek()) {
            context.setVariable(label, null);
        }
        scope.pop();
    }
}
