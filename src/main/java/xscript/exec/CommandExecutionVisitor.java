package xscript.exec;

import el.Decoder;
import el.Instance;
import xscript.model.*;
import xscript.model.Set;

import java.util.*;

public class CommandExecutionVisitor implements CommandVisitor, ValueVisitor<Instance> {

    private static final Scanner input = new Scanner(System.in);

    private static final Decoder decoder = new Decoder();

    private Decoder.ContextBuilder ctx;

    private SubroutineRegistry registry;

    public CommandExecutionVisitor(SubroutineRegistry registry) {
        this.registry = registry;
    }

    public Decoder.ContextBuilder getCtx() {
        return ctx;
    }

    public void setCtx(Decoder.ContextBuilder ctx) {
        this.ctx = ctx;
    }

    @Override
    public void visit(Print print) {
        System.out.println(decoder.decode(print.getValueExpression(),ctx));
    }

    @Override
    public void visit(Prompt prompt) {
        System.out.println(decoder.decode(prompt.getMessageExpression(),ctx));
        String inValue = input.nextLine();
        ctx.set(inValue,prompt.getVariableName().split("\\."));
    }

    @Override
    public void visit(Try aTry) {
        try {
            executeBlock(aTry.getTryBlock());
        } catch (ThrownException te) {
            Catch catchBlock = aTry.getCatchBlock();
            if (catchBlock != null) {
                Set setException = new Set();
                setException.setValue(te.getValue());
                setException.setVariable(catchBlock.getError());
                setException.accept(this);
                executeBlock(catchBlock);
                dissoc(ctx,catchBlock.getError());
            }
        } finally {
            executeBlock(aTry.getFinallyBlock());
        }
    }

    @Override
    public void visit(While aWhile) {
        while(test(aWhile.getWhileExpression())) {
            executeBlock(aWhile);
        }
    }

    @Override
    public void visit(Set set) {
        // todo ;
    }

    @Override
    public void visit(If anIf) {
        // todo ;
    }

    @Override
    public void visit(Call call) {
        String procedureName = call.getProcedureName();
        Procedure procedure = registry.getProcedure(procedureName);
        if (procedure == null) {
            throw new EvaluationException("Procedure not found: " + procedureName);
        }
        executeSubroutine(procedure,"Procedure", procedureName, call.getArgs());
    }

    @Override
    public void visit(Do aDo) {
        executeBlock(aDo);
    }

    @Override
    public void visit(DoWhile doWhile) {
        do {
            executeBlock(doWhile);
        } while (test(doWhile.getWhileExpression()));
    }

    @Override
    public void visit(ForEach forEach) {
        // todo ;
    }

    private Set buildNumber(String name, String expression) {
        Set out = new Set();
        out.setVariable(name);
        PrimitiveValue value = new PrimitiveValue();
        value.setType(PrimitiveType.NUMBER);
        value.setValue(expression);
        return out;
    }

    @Override
    public void visit(For aFor) {
        Set init = buildNumber(aFor.getVarName(), aFor.getInitExpression());
        Set step = buildNumber(aFor.getVarName(), aFor.getStepExpression());

        init.accept(this);
        while(test(aFor.getWhileExpression())) {
            try {
                executeBlock(aFor);
            } catch (BreakException e) {
                break;
            } catch (ContinueException e) {
                // do nothing - just catch for control
            }
            step.accept(this);
        }
        dissoc(ctx,aFor.getVarName());
    }

    @Override
    public void visit(Throw aThrow) {
        throw new ThrownException(aThrow.getValue());
    }

    @Override
    public void visit(Break aBreak) {
        throw BreakException.INSTANCE;
    }

    @Override
    public void visit(Continue aContinue) {
        throw ContinueException.INSTANCE;
    }


    @Override
    public Instance visit(MapValue mapValue) {
        Map<String,Instance> out = new HashMap<>();
        for(MapEntry entry : mapValue.getEntries()) {
            if (out.containsKey(entry.getKey()))
                throw new EvaluationException("repeated key: " + entry.getKey());
            out.put(entry.getKey(),entry.getValue().accept(this));
        }
        return new Instance.MapInstance(out);
    }

    @Override
    public Instance visit(PrimitiveValue primitiveValue) {
        return primitiveValue.getType().parse(String.valueOf(decoder.decode(primitiveValue.getValue(),ctx)));
    }

    @Override
    public Instance visit(NullValue nullValue) {
        return null;
    }

    @Override
    public Instance visit(ListValue listValue) {
        List<Instance> out = new ArrayList<>();
        for(Value value : listValue.getItems()) {
            out.add(value.accept(this));
        }
        return new Instance.ListInstance(out);
    }

    @Override
    public Instance visit(CallFn callFn) {
        String functionName = callFn.getFunctionName();
        Function function = registry.getFunction(functionName);
        if (function == null) {
            throw new EvaluationException("Function not found: " + functionName);
        }
        CommandExecutionVisitor visitor = executeSubroutine(function,"Function", functionName,callFn.getArgs());
        return function.getReturnValue().getValue().accept(visitor);
    }

    @Override
    public Instance visit(DateValue dateValue) {
        // todo
        return null;
    }

    private boolean test(String expression) {
        return Boolean.valueOf(String.valueOf(decoder.decode(expression,ctx)));
    }

    private void dissoc(Decoder.ContextBuilder ctx, String variable) {
        // todo - remove variable from context
    }

    private CommandExecutionVisitor executeSubroutine(Subroutine subroutine, String subtype, String name, List<Argument> args) {
        CommandExecutionVisitor visitor = new CommandExecutionVisitor(registry);
        visitor.setCtx(ctx.copy());
        // todo - processing arg values;
        subroutine.getDoBlock().accept(visitor);
        return visitor;
    }

    private void executeBlock(Block block) {
        for (Command command : block.getCommands()) {
            command.accept(this);
        }
    }
}
