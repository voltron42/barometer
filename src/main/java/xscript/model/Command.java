package xscript.model;

public interface Command {
    void accept(CommandVisitor visitor);
}
