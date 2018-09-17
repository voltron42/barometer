package xscript.exec;

import xscript.model.Program;

public class Executor {

    public void execute(Program program) {
        SubroutineRegistry registry = SubroutineRegistry.build(program.getSubroutines());
        CommandExecutionVisitor exec = new CommandExecutionVisitor(registry);
        program.getMainRoutine().accept(exec);
    }
}
