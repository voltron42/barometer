package barometer.exec;

import barometer.model.common.Function;
import barometer.model.common.Procedure;
import barometer.model.common.Subroutine;
import barometer.model.common.SubroutineVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubroutineRegistry {

    private final Map<String,Procedure> procedures;
    private final Map<String,Function> functions;

    private SubroutineRegistry(Map<String, Procedure> procedures, Map<String, Function> functions) {
        this.procedures = procedures;
        this.functions = functions;
    }

    public Procedure getProcedure(String name) {
        return procedures.get(name);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }

    public static SubroutineRegistry build(List<Subroutine> subroutines) {
        RegistryBuilderVisitor visitor = new RegistryBuilderVisitor();
        for (Subroutine subroutine : subroutines) {
            subroutine.accept(visitor);
        }
        return new SubroutineRegistry(visitor.procedures,visitor.functions);
    }

    private static class RegistryBuilderVisitor implements SubroutineVisitor {

        private Map<String,Procedure> procedures = new HashMap<>();
        private Map<String,Function> functions = new HashMap<>();

        @Override
        public void visit(Procedure procedure) {
            procedures.put(procedure.getName(),procedure);
        }

        @Override
        public void visit(Function function) {
            functions.put(function.getName(),function);
        }
    }
}
