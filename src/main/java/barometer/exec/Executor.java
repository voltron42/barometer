package barometer.exec;

import barometer.el.Decoder;
import barometer.model.common.Program;

public class Executor {

    public void execute(Program program) {
        Decoder.ContextBuilder ctx = new Decoder.ContextBuilder();
        SubroutineRegistry registry = SubroutineRegistry.build(program.getSubroutines());
        CommandExecutionVisitor exec = new CommandExecutionVisitor(registry);
        exec.setCtx(ctx);
    }
}
