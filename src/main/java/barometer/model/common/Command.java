package barometer.model.common;

public interface Command {
    void accept(CommandVisitor visitor);
}
