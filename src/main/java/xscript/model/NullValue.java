package xscript.model;

public enum NullValue implements Value {
    INSTANCE;

    @Override
    public Object accept(ValueVisitor visitor) {
        return visitor.visit(this);
    }
}
