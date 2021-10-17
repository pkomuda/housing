package pl.dmcs.pkomuda.exceptions;

public class AccountAlreadyExistsException extends ApplicationBaseException {

    public static final String KEY_ACCOUNT_EXISTS = "error.account.exists";

    public AccountAlreadyExistsException() {
        super(KEY_ACCOUNT_EXISTS);
    }

    public AccountAlreadyExistsException(Throwable cause) {
        super(KEY_ACCOUNT_EXISTS, cause);
    }
}
