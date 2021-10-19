package pl.dmcs.pkomuda.services;

import pl.dmcs.pkomuda.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.model.Account;

public interface AccountService {

    void addAccount(Account account) throws ApplicationBaseException;
    void confirmAccount(String token) throws ApplicationBaseException;
}
