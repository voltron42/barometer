package xscript.model;

public interface Value {
    Object accept(ValueVisitor visitor);
}
