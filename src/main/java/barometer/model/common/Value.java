package barometer.model.common;

public interface Value {
    Object accept(ValueVisitor visitor);
}
