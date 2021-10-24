package pl.dmcs.pkomuda.housing.services;

import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Account;

import java.util.List;

public interface AccountService {

    void addAccount(Account account) throws ApplicationBaseException;
    void register(Account account) throws ApplicationBaseException;
    void confirmAccount(String token) throws ApplicationBaseException;
    List<Account> getAllAccounts();
}
