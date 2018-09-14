package xscript.model;

import el.Instance;

public interface Value {
    <T> T accept(ValueVisitor<T> visitor);
}
