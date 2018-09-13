package barometer.model.common;

public class Break implements Command {
    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
