package el;

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
    Map<String,Instance> mapValue();

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
        DATE(java.util.Date.class,java.sql.Date.class,java.sql.Timestamp.class) {
            @Override
            protected Instance from(Object obj) {
                return new DateTimeInstance((java.util.Date) obj);
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
                List in = (List) obj;
                List<Instance> out = new ArrayList<>();
                for (Object item : in) {
                    out.add(fromObj(item));
                }
                return new ListInstance(out);
            }
        },
        MAP(Map.class) {
            @Override
            protected Instance from(Object obj) {
                Map<?,?> in = (Map) obj;
                Map<String,Instance> out = new HashMap<>();
                for (Map.Entry entry : in.entrySet()) {
                    out.put(String.valueOf(entry.getKey()), fromObj(entry.getValue()));
                }
                return new MapInstance(out);
            }
        },
        ;

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
    }

    class ListInstance implements Instance {
        private List<Instance> value;

        public ListInstance(List<Instance> value) {
            this.value = value;
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
    }

    public static class MapInstance implements Instance {
        private Map<String, Instance> value;

        public MapInstance(Map<String, Instance> value) {
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
    }
}
