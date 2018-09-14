package xscript.exec;

import el.Decoder;
import xscript.model.Program;

public class Executor {

    public void execute(Program program) {
        Decoder.ContextBuilder ctx = new Decoder.ContextBuilder();
        SubroutineRegistry registry = SubroutineRegistry.build(program.getSubroutines());
        CommandExecutionVisitor exec = new CommandExecutionVisitor(registry);
        exec.setCtx(ctx);
        program.getMainRoutine().accept(exec);
    }
}
