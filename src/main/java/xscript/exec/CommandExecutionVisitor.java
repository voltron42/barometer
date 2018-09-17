package xscript.exec;

import common.time.DateTimeFormats;
import el2.Context;
import el2.Instance;
import xscript.model.*;
import xscript.model.Set;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommandExecutionVisitor implements CommandVisitor, ValueVisitor<Instance> {

    private static final Scanner input = new Scanner(System.in);

    private final Context context;

    private final SubroutineRegistry registry;

    public CommandExecutionVisitor(SubroutineRegistry registry) {
        this(new Context(), registry);
    }

    private CommandExecutionVisitor(Context context, SubroutineRegistry registry) {
        this.context = context;
        this.registry = registry;
    }

    @Override
    public void visit(Print print) {
        System.out.println(context.decode(print.getValueExpression()));
    }

    @Override
    public void visit(Prompt prompt) {
        System.out.println(context.decode(prompt.getMessageExpression()));
        String inValue = input.nextLine();
        context.set(prompt.getVariableName(), new Instance.Text(inValue));
    }

    @Override
    public void visit(Try aTry) {
        try {
            executeBlock(aTry.getTryBlock());
        } catch (ThrownException te) {
            Catch catchBlock = aTry.getCatchBlock();
            if (catchBlock != null) {
                context.scope();
                Set setException = new Set();
                setException.setValue(te.getValue());
                setException.setVariable(catchBlock.getError());
                setException.accept(this);
                executeBlock(catchBlock);
                context.descope();
            }
        } finally {
            context.scope();
            executeBlock(aTry.getFinallyBlock());
            context.descope();
        }
    }

    @Override
    public void visit(While aWhile) {
        while(test(aWhile.getWhileExpression())) {
            context.scope();
            executeBlock(aWhile);
            context.descope();
        }
    }

    @Override
    public void visit(Set set) {
        context.set(set.getVariable(),set.getValue().accept(this));
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
            context.scope();
            executeBlock(doWhile);
            context.descope();
        } while (test(doWhile.getWhileExpression()));
    }

    private static class ForEachEntry {
        private final Instance index;
        private final Instance item;

        private ForEachEntry(String index, Instance item) {
            this.index = new Instance.Text(index);
            this.item = item;
        }

        private ForEachEntry(int index, Instance item) {
            this.index = new Instance.IntegerInstance((long) index);
            this.item = item;
        }

        public Instance getIndex() {
            return index;
        }

        public Instance getItem() {
            return item;
        }
    }

    @Override
    public void visit(ForEach forEach) {
        String collectionExpression = forEach.getCollectionExpression();
        String indexName = forEach.getIndexName();
        String itemName = forEach.getItemName();
        Instance collection = context.decode(collectionExpression);
        List<ForEachEntry> entries = new ArrayList<>();
        switch (collection.getType()) {
            case LIST:
                List<Instance> items = collection.listValue();
                int size = items.size();
                for (int x = 0; x < size; x++) {
                    entries.add(new ForEachEntry(x,items.get(x)));
                }
                break;
            case MAP:
                for (Map.Entry<String, Instance> entry : collection.mapValue().entrySet()) {
                    entries.add(new ForEachEntry(entry.getKey(), entry.getValue()));
                }
                break;
            default:
                throw new IllegalArgumentException("Expression does not resolve to a collection: " + collectionExpression);
        }
        for (ForEachEntry entry : entries) {
            context.scope();
            context.set(itemName, entry.getItem());
            if (null != indexName) {
                context.set(indexName, entry.getIndex());
            }
            try {
                executeBlock(forEach);
            } catch (BreakException e) {
                break;
            } catch (ContinueException e) {
                // do nothing - just catch for control
            }
            context.descope();
        }
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
        context.scope();
        Set init = buildNumber(aFor.getVarName(), aFor.getInitExpression());
        Set step = buildNumber(aFor.getVarName(), aFor.getStepExpression());

        init.accept(this);
        while(test(aFor.getWhileExpression())) {
            context.scope();
            try {
                executeBlock(aFor);
            } catch (BreakException e) {
                break;
            } catch (ContinueException e) {
                // do nothing - just catch for control
            }
            step.accept(this);
            context.descope();
        }
        context.descope();
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
    public void visit(Remove remove) {
        String keyExpr = remove.getKey();
        String mapExpr = remove.getMap();
        Instance key = context.decode(keyExpr);
        Instance mapInst = context.decode(mapExpr);
        if (!Instance.Type.MAP.equals(mapInst.getType())) {
            throw new IllegalArgumentException("remove key expression does not resolve to a string");
        }
        if (!Instance.Type.TEXT.equals(key.getType())) {
            throw new IllegalArgumentException("remove map expression does not resolve to a map");
        }
        Instance.MapInstance map = (Instance.MapInstance) mapInst;
        map.removeFrom(key.stringValue());
    }

    @Override
    public void visit(Put put) {
        String keyExpr = put.getKey();
        Value valueExpr = put.getValue();
        String mapExpr = put.getMap();
        Instance key = context.decode(keyExpr);
        Instance mapInst = context.decode(mapExpr);
        Instance value = valueExpr.accept(this);
        if (!Instance.Type.MAP.equals(mapInst.getType())) {
            throw new IllegalArgumentException("put key expression does not resolve to a string");
        }
        if (!Instance.Type.TEXT.equals(key.getType())) {
            throw new IllegalArgumentException("put map expression does not resolve to a map");
        }
        Instance.MapInstance map = (Instance.MapInstance) mapInst;
        map.putInto(key.stringValue(),value);
    }

    @Override
    public void visit(Pop pop) {
        // TODO
    }

    @Override
    public void visit(Push push) {
        String indexExpr = push.getIndex();
        String listExpr = push.getList();
        Value value = push.getValue();
        Instance listInst = context.decode(listExpr);
        Instance item = value.accept(this);
        if (!Instance.Type.LIST.equals(listInst.getType())) {
            throw new IllegalArgumentException("push list expression does not resolve to a list");
        }
        Instance.ListInstance list = (Instance.ListInstance) listInst;
        if (null == indexExpr) {
            list.add(item);
        } else {
            Instance indexInst = context.decode(indexExpr);
            if (!Instance.Type.LONG.equals(indexInst.getType())) {
                throw new IllegalArgumentException("push index expression does not resolve to a integer");
            }
            list.set(indexInst.integerValue(),item);
        }
    }


    @Override
    public Instance visit(MapValue mapValue) {
        Map<String,Instance> out = new HashMap<>();

        for(MapEntry entry : mapValue.getEntries()) {
            if (out.containsKey(entry.getKey()))
                throw new EvaluationException("repeated key: " + entry.getKey());
            out.put(entry.getKey(),entry.getValue().accept(this));
        }
        return new Instance.MapInstance(in, out);
    }

    @Override
    public Instance visit(PrimitiveValue primitiveValue) {
        return primitiveValue.getType().parse(String.valueOf(context.decode(primitiveValue.getValue())));
    }

    @Override
    public Instance visit(NullValue nullValue) {
        return new Instance.Null();
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
        String format = dateValue.getFormat();
        String value = dateValue.getValue();
        Date date = null;
        try {
            if (null == format) {
                date = DateTimeFormats.DATE_TIME_ROBUST.parse(value);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                date = formatter.parse(value);
            }
            return new Instance.DateTimeInstance(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean test(String expression) {
        return Boolean.valueOf(String.valueOf(context.decode(expression)));
    }

    private CommandExecutionVisitor executeSubroutine(Subroutine subroutine, String subtype, String name, List<Argument> args) {
        CommandExecutionVisitor visitor = new CommandExecutionVisitor(context.rescope(), registry);
        Map<String,Value> argMap = new HashMap<>();
        ListValue varArgs = new ListValue();
        varArgs.setItems(new ArrayList<>());
        String varArgName = null;
        if (subroutine.getVarParam() != null) {
            varArgName = subroutine.getVarParam().getName();
        }
        for (Argument arg : args) {
            if (arg.getName().equals(varArgName)) {
                varArgs.getItems().add(arg.getValue());
            } else {
                argMap.put(arg.getName(), arg.getValue());
            }
        }
        Do init = new Do();
        for (Param param : subroutine.getParams()) {
            String paramName = param.getName();
            Value value = param.getDefaultValue();
            Set setter = new Set();
            init.getCommands().add(setter);
            setter.setVariable(paramName);
            if (argMap.containsKey(paramName)) {
                value = argMap.get(paramName);
            }
            if (value == null) {
                setter.setValue(NullValue.INSTANCE);
            } else {
                setter.setValue(value);
            }
        }
        Set setter = new Set();
        setter.setVariable(varArgName);
        setter.setValue(varArgs);
        init.getCommands().add(setter);
        init.accept(visitor);
        subroutine.getDoBlock().accept(visitor);
        return visitor;
    }

    private void executeBlock(Block block) {
        for (Command command : block.getCommands()) {
            command.accept(this);
        }
    }
}
