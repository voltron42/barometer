package barometer.exec;

public class BreakException extends RuntimeException {
    public static final BreakException INSTANCE = new BreakException();

    private BreakException(){}
}
