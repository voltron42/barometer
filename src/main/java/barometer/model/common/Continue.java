package barometer.model.common;

public class Continue implements Command {
    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
