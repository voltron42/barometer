package el2;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public interface Instance {
    Type getType();
    boolean booleanValue();
    long integerValue();
    double decimalValue();
    String stringValue();
    Date dateValue();
    Path filePathValue();
    List<Instance> listValue();
    Map<String, Instance> mapValue();
    void put(Map<Object, Object> map, String label);
    void append(List<Object> list);

    static Instance create(Object obj) {
        return Type.fromObj(obj);
    }

    enum Type {
        TEXT(String.class) {
            @Override
            protected Instance from(Object obj) {
                return new Text(String.valueOf(obj));
            }
        },
        BOOLEAN(boolean.class, Boolean.class) {
            @Override
            protected Instance from(Object obj) {
                return new BoolInstance((Boolean) obj);
            }
        },
        LONG(long.class,Long.class,int.class,Integer.class,short.class,Short.class,byte.class,Byte.class) {
            @Override
            protected Instance from(Object obj) {
                return new IntegerInstance(((Number) obj).longValue());
            }
        },
        DOUBLE(double.class,Double.class,float.class,Float.class) {
            @Override
            protected Instance from(Object obj) {
                return new DecimalInstance(((Number) obj).doubleValue());
            }
        },
        DATE(Date.class,java.sql.Date.class,java.sql.Timestamp.class) {
            @Override
            protected Instance from(Object obj) {
                return new DateTimeInstance((Date) obj);
            }
        },
        FILE(File.class, Path.class) {
            @Override
            protected Instance from(Object obj) {
                if (obj instanceof Path) {
                    return new FilePathInstance((Path) obj);
                } else if (obj instanceof File) {
                    return new FilePathInstance((File) obj);
                } else {
                    throw new IllegalArgumentException(String.valueOf(obj));
                }
            }
        },
        LIST(List.class) {
            @Override
            protected Instance from(Object obj) {
                List<Object> in = (List<Object>) obj;
                return new ListInstance(in);
            }
        },
        MAP(Map.class) {
            @Override
            protected Instance from(Object obj) {
                Map<?,?> in = (Map) obj;
                Map<String, Instance> out = new HashMap<>();
                for (Map.Entry entry : in.entrySet()) {
                    out.put(String.valueOf(entry.getKey()), fromObj(entry.getValue()));
                }
                return new MapInstance(in,out);
            }
        },
        NULL(Void.class, null) {
            @Override
            protected Instance from(Object obj) {
                return new Null();
            }
        };

        private Set<Class<?>> classes;

        Type(Class<?>... classes) {
            this.classes = new HashSet<>(Arrays.asList(classes));
        }

        static Instance fromObj(Object obj) {
            for (Type type : Type.values()) {
                for (Class<?> cls : type.classes) {
                    if (cls.isInstance(obj)) {
                        return type.from(obj);
                    }
                }
            }
            throw new IllegalArgumentException(obj.getClass().getCanonicalName());
        }

        protected abstract Instance from(Object obj);

    }
    class Text implements Instance {
        private String value;

        public Text(String value) {
            this.value = value;
        }

        @Override
        public Type getType() {
            return Type.TEXT;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double decimalValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String stringValue() {
            return value;
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            return null;
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label,value);
        }

        @Override
        public void append(List<Object> list) {
            list.add(value);
        }
    }

    class BoolInstance implements Instance {
        private Boolean bool;

        public BoolInstance(Boolean bool) {
            this.bool = bool;
        }

        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }

        @Override
        public boolean booleanValue() {
            return bool;
        }

        @Override
        public long integerValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double decimalValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String stringValue() {
            return bool.toString();
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            return null;
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label,bool);
        }

        @Override
        public void append(List<Object> list) {
            list.add(bool);
        }
    }

    class IntegerInstance implements Instance {
        private Long value;

        public IntegerInstance(Long value) {
            this.value = value;
        }

        @Override
        public Type getType() {
            return Type.LONG;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            return value;
        }

        @Override
        public double decimalValue() {
            return value.doubleValue();
        }

        @Override
        public String stringValue() {
            return value.toString();
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label,value);
        }

        @Override
        public void append(List<Object> list) {
            list.add(value);
        }
    }

    class DecimalInstance implements Instance {
        private Double value;

        public DecimalInstance(Double value) {
            this.value = value;
        }

        @Override
        public Type getType() {
            return Type.DOUBLE;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            return value.longValue();
        }

        @Override
        public double decimalValue() {
            return value;
        }

        @Override
        public String stringValue() {
            return value.toString();
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label, value);
        }

        @Override
        public void append(List<Object> list) {
            list.add(value);
        }
    }

    class DateTimeInstance implements Instance {
        private Date value;

        public DateTimeInstance(Date value) {
            this.value = value;
        }

        @Override
        public Type getType() {
            return Type.DATE;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            return value.getTime();
        }

        @Override
        public double decimalValue() {
            return value.getTime();
        }

        @Override
        public String stringValue() {
            return value.toString();
        }

        @Override
        public Date dateValue() {
            return value;
        }

        @Override
        public Path filePathValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label, value);
        }

        @Override
        public void append(List<Object> list) {
            list.add(value);
        }
    }

    class FilePathInstance implements Instance {
        private final Path value;

        public FilePathInstance(Path path) {
            this.value = path;
        }
        public FilePathInstance(File file) {
            this.value = file.toPath();
        }

        @Override
        public Type getType() {
            return Type.FILE;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double decimalValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String stringValue() {
            return String.valueOf(value);
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            return value;
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label, value);
        }

        @Override
        public void append(List<Object> list) {
            list.add(value);
        }
    }

    class ListInstance implements Instance {
        private List<Object> list;
        private List<Instance> value;

        public ListInstance(List<Object> list) {
            this.list = list;
        }

        public static ListInstance fromInstanceList(List<Instance> value) {
            List<Object> list = new ArrayList<>();
            for (Instance inst : value) {
                inst.append(list);
            }
            return new ListInstance(list);
        }

        @Override
        public Type getType() {
            return Type.LIST;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double decimalValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String stringValue() {
            return value.toString();
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Instance> listValue() {
            return value;
        }

        @Override
        public Map<String, Instance> mapValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label,copy());
        }

        @Override
        public void append(List<Object> list) {
            list.add(copy());
        }

        private List<Object> copy() {
            List<Object> out = new ArrayList<>();
            for (Instance inst : value) {
                inst.append(out);
            }
            return out;
        }

        public void add(Instance item) {
            // TODO ;
        }

        public void set(long index, Instance item) {
            // TODO ;
        }
    }

    public static class MapInstance implements Instance {
        private Map<Object, Object> myMap;
        private Map<?, ?> in;
        private Map<String, Instance> value;

        public MapInstance(Map<?, ?> in, Map<String, Instance> value) {
            myMap = in;
            this.in = in;
            this.value = value;
        }

        @Override
        public Type getType() {
            return Type.MAP;
        }

        @Override
        public boolean booleanValue() {
            return value != null;
        }

        @Override
        public long integerValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double decimalValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String stringValue() {
            return value.entrySet().toString();
        }

        @Override
        public Date dateValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Path filePathValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Instance> listValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Instance> mapValue() {
            return value;
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label, copy());
        }

        private Map<Object,Object> copy() {
            Map<Object,Object> out = new HashMap<>();
            for(Map.Entry<String, Instance> entry : value.entrySet()) {
                entry.getValue().put(out, entry.getKey());
            }
            return out;
        }

        @Override
        public void append(List<Object> list) {
            list.add(copy());
        }

        public void removeFrom(String key) {

        }
        public void putInto(String key, Instance value) {

        }



    }

    public class Null implements Instance {
        @Override
        public Type getType() {
            return Type.NULL;
        }

        @Override
        public boolean booleanValue() {
            return false;
        }

        @Override
        public long integerValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double decimalValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String stringValue() {
            return null;
        }

        @Override
        public Date dateValue() {
            return null;
        }

        @Override
        public Path filePathValue() {
            return null;
        }

        @Override
        public List<Instance> listValue() {
            return null;
        }

        @Override
        public Map<String, Instance> mapValue() {
            return null;
        }

        @Override
        public void put(Map<Object, Object> map, String label) {
            map.put(label,null);
        }

        @Override
        public void append(List<Object> list) {
            list.add(null);
        }
    }
}
