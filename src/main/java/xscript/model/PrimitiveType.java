package xscript.model;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;

public enum PrimitiveType {
    STRING {
        @Override
        public Object parse(String value) {
            return value;
        }
    },
    NUMBER {
        @Override
        public Object parse(String value) {
            return new BigDecimal(value);
        }
    },
    BOOLEAN {
        @Override
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },
    FILE {
        @Override
        public Object parse(String value) {
            return new File(value);
        }
    },
    PATH {
        @Override
        public Object parse(String value) {
            return Paths.get(value);
        }
    };

    public abstract Object parse(String value);
}
