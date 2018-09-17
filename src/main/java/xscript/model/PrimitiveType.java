package xscript.model;

import el2.Instance;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;

public enum PrimitiveType {
    STRING {
        @Override
        public Instance parse(String value) {
            return Instance.create(value);
        }
    },
    NUMBER {
        @Override
        public Instance parse(String value) {
            try {
                return Instance.create(Double.parseDouble(value));
            } catch (NumberFormatException n) {
                try {
                    return Instance.create(Double.parseDouble(value));
                } catch (NumberFormatException n2) {
                    throw new IllegalArgumentException(n2);
                }
            }
        }
    },
    BOOLEAN {
        @Override
        public Instance parse(String value) {
            return Instance.create(Boolean.valueOf(value));
        }
    },
    PATH {
        @Override
        public Instance parse(String value) {
            return Instance.create(Paths.get(value));
        }
    };

    public abstract Instance parse(String value);
}
