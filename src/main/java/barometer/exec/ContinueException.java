package barometer.exec;

public class ContinueException extends RuntimeException {
    public static final ContinueException INSTANCE = new ContinueException();

    private ContinueException(){}
}
