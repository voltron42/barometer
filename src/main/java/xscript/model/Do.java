package xscript.model;

public class Do extends Block implements Command {
    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
