package pl.dmcs.pkomuda.housing.exceptions;

public class FlatNotFoundException extends ApplicationBaseException {

    public static final String KEY_FLAT_NOT_FOUND = "error.flat.not.found";

    public FlatNotFoundException() {
        super(KEY_FLAT_NOT_FOUND);
    }

    public FlatNotFoundException(Throwable cause) {
        super(KEY_FLAT_NOT_FOUND, cause);
    }
}
