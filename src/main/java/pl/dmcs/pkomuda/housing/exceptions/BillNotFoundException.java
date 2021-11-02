package pl.dmcs.pkomuda.housing.exceptions;

public class BillNotFoundException extends ApplicationBaseException {

    public static final String KEY_BILL_NOT_FOUND = "error.bill.not.found";

    public BillNotFoundException() {
        super(KEY_BILL_NOT_FOUND);
    }

    public BillNotFoundException(Throwable cause) {
        super(KEY_BILL_NOT_FOUND, cause);
    }
}
