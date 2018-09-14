package xscript.model;

public class Break implements Command {
    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
