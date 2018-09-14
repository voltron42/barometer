package xscript.model;

public enum NullValue implements Value {
    INSTANCE;

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
