package xscript.exec;

import xscript.model.Value;

public class ThrownException extends RuntimeException {

    private Value value;

    public ThrownException(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
