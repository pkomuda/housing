package pl.dmcs.pkomuda.exceptions;

public class AccountNotFoundException extends ApplicationBaseException {

    public static final String KEY_ACCOUNT_NOT_FOUND = "error.account.not.found";

    public AccountNotFoundException() {
        super(KEY_ACCOUNT_NOT_FOUND);
    }

    public AccountNotFoundException(Throwable cause) {
        super(KEY_ACCOUNT_NOT_FOUND, cause);
    }
}
