package xscript.model;

public interface ValueVisitor<T> {
    T visit(MapValue mapValue);

    T visit(PrimitiveValue primitiveValue);

    T visit(NullValue nullValue);

    T visit(ListValue listValue);

    T visit(CallFn callFn);

    T visit(DateValue dateValue);
}
