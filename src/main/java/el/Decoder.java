package el;

import common.time.DateTimeFormats;
import de.odysseus.el.ExpressionFactoryImpl;

import javax.el.*;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class Decoder {

    private final ExpressionFactory factory;

    public Decoder() {
        factory = ExpressionFactoryImpl.newInstance();
    }

    public String decode(String expression, ContextBuilder cb) {
        ELContext ctx = new CTX(cb.context);
        ValueExpression ve = factory.createValueExpression(ctx, expression, Object.class);
        return String.valueOf(ve.getValue(ctx));
    }

    public static class ContextBuilder {
        private final Map<Object, Object> context;

        public ContextBuilder() {
            context = new HashMap<>();
        }

        public ContextBuilder set(String value, String... path) {
            return apply(value, path);
        }

        public ContextBuilder set(Number value, String... path) {
            return apply(value, path);
        }

        public ContextBuilder set(Boolean value, String... path) {
            return apply(value, path);
        }

        public ContextBuilder set(Date value, String... path) {
            return apply(contextify(value), path);
        }

        private ContextBuilder apply(Object value, String[] path) {
            List<String> steps = new ArrayList<>(Arrays.asList(path));
            String last = steps.remove(path.length - 1);
            Map<Object, Object> temp = context;
            for (String step : steps) {
                Map<Object, Object> previous = temp;
                temp = (Map<Object, Object>) previous.get(step);
                if (temp == null) {
                    temp = new HashMap<>();
                    previous.put(step, temp);
                }
            }
            temp.put(last, value);
            return this;
        }
        
        private void deepCopyTo(Map<Object, Object> from, Map<Object, Object> to) {
            for (Map.Entry<Object,Object> entry : from.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Map) {
                    Map<Object, Object> fromMap = (Map<Object, Object>) value;
                    Map<Object, Object> toMap = new HashMap<>();
                    deepCopyTo(fromMap, toMap);
                    to.put(key,toMap);
                } else {
                    to.put(key,value);
                }
            }
        }

        public ContextBuilder copy() {
            // todo - make paths excludeable
            ContextBuilder out = new ContextBuilder();
            deepCopyTo(this.context, out.context);
            return out;
        }

        private static Map<Object, Object> contextify(Date start) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(start.getTime());
            Map<Object, Object> ctx = new HashMap<>();
            ctx.put("year", calendar.get(Calendar.YEAR));
            ctx.put("month", calendar.get(Calendar.MONTH));
            ctx.put("day", calendar.get(Calendar.DAY_OF_MONTH));
            ctx.put("weekday", calendar.get(Calendar.DAY_OF_WEEK));
            ctx.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
            ctx.put("minute", calendar.get(Calendar.MINUTE));
            ctx.put("second", calendar.get(Calendar.SECOND));
            ctx.put("utc", calendar.getTimeInMillis());
            ctx.put("date", DateTimeFormats.DATE_STAMP.format(start));
            ctx.put("time", DateTimeFormats.TIME_STAMP.format(start));
            ctx.put("datehour", DateTimeFormats.DATE_HOUR_STAMP.format(start));
            ctx.put("datetime", DateTimeFormats.DATE_TIME_STAMP.format(start));
            return ctx;
        }
    }

    private static class CTX extends ELContext {

        private final ELResolver resolver;
        private final FunctionMapper funcMapper;
        private final VariableMapper varMapper;

        public CTX(Map<Object, Object> ctx) {
            this.resolver = new Resolver(ctx);
            funcMapper = new FunctionMapper() {
                public Map<String, Method> map;

                @Override
                public Method resolveFunction(String prefix, String local) {
                    return map.get(prefix + ":" + local);
                }
            };
            varMapper = new VariableMapper() {
                private Map<String, ValueExpression> expressions = new HashMap<>();

                @Override
                public ValueExpression resolveVariable(String variable) {
                    return expressions.get(variable);
                }

                @Override
                public ValueExpression setVariable(String variable, ValueExpression expression) {
                    return expressions.put(variable, expression);
                }
            };
        }

        @Override
        public ELResolver getELResolver() {
            return resolver;
        }

        @Override
        public FunctionMapper getFunctionMapper() {
            return funcMapper;
        }

        @Override
        public VariableMapper getVariableMapper() {
            return varMapper;
        }
    }

    private static class Resolver extends ELResolver {

        private ELResolver delegate = new MapELResolver();
        private Map<Object, Object> userMap;

        public Resolver(Map<Object, Object> userMap) {
            this.userMap = userMap;
        }

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            if(base==null) {
                base = userMap;
            }
            return delegate.getValue(context, base, property);
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext context, Object base) {
            if(base==null) {
                base = userMap;
            }
            return delegate.getCommonPropertyType(context, base);
        }

        @Override
        public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
                                                                 Object base) {
            if(base==null) {
                base = userMap;
            }
            return delegate.getFeatureDescriptors(context, base);
        }

        @Override
        public Class<?> getType(ELContext context, Object base, Object property) {
            if(base==null) {
                base = userMap;
            }
            return delegate.getType(context, base, property);
        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property) {
            if(base==null) {
                base = userMap;
            }
            return delegate.isReadOnly(context, base, property);
        }

        @Override
        public void setValue(ELContext context, Object base, Object property, Object value) {
            if(base==null) {
                base = userMap;
            }
            delegate.setValue(context, base, property, value);
        }

    }
}
