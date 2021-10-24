package pl.dmcs.pkomuda.housing.exceptions;

public class ApplicationBaseException extends Exception {

    public static final String KEY_DEFAULT  = "error.default";

    public ApplicationBaseException(String message) {
        super(message);
    }

    public ApplicationBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationBaseException(Throwable cause) {
        super(KEY_DEFAULT, cause);
    }
}
