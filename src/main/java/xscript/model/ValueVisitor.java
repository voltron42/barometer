package xscript.model;

public interface ValueVisitor {
    Object visit(MapValue mapValue);

    Object visit(PrimitiveValue primitiveValue);

    Object visit(NullValue nullValue);

    Object visit(ListValue listValue);

    Object visit(CallFn callFn);
}
