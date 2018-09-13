package barometer.exec;

import barometer.el.Decoder;
import barometer.model.common.*;
import barometer.model.common.Set;

import java.util.*;

public class CommandExecutionVisitor implements CommandVisitor, ValueVisitor {

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

    }

    @Override
    public void visit(While aWhile) {

    }

    @Override
    public void visit(Set set) {

    }

    @Override
    public void visit(If anIf) {

    }

    @Override
    public void visit(Call call) {

    }

    @Override
    public void visit(Do aDo) {

    }

    @Override
    public void visit(DoWhile doWhile) {

    }

    @Override
    public void visit(ForEach forEach) {

    }

    @Override
    public void visit(For aFor) {

    }

    @Override
    public void visit(Throw aThrow) {

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
    public Object visit(MapValue mapValue) {
        Map<String,Object> out = new HashMap<>();
        for(MapEntry entry : mapValue.getEntries()) {
            if (out.containsKey(entry.getKey()))
                throw new EvaluationException("repeated key: " + entry.getKey());
            out.put(entry.getKey(),entry.getValue().accept(this));
        }
        return null;
    }

    @Override
    public Object visit(PrimitiveValue primitiveValue) {
        return primitiveValue.getType().parse(decoder.decode(primitiveValue.getValue(),ctx));
    }

    @Override
    public Object visit(NullValue nullValue) {
        return null;
    }

    @Override
    public Object visit(ListValue listValue) {
        List<Object> out = new ArrayList<>();
        for(Value value : listValue.getItems()) {
            out.add(value.accept(this));
        }
        return out;
    }

    @Override
    public Object visit(CallFn callFn) {
        Function function = registry.getFunction(callFn.getFunctionName());
        if (function == null) {
            throw new EvaluationException("Function not found: " + callFn.getFunctionName());
        }
        // todo;
        return null;
    }
}
