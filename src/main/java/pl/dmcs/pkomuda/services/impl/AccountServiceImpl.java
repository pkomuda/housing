package pl.dmcs.pkomuda.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.exceptions.AccountAlreadyExistsException;
import pl.dmcs.pkomuda.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.model.Account;
import pl.dmcs.pkomuda.repositories.AccountRepository;
import pl.dmcs.pkomuda.services.AccountService;
import pl.dmcs.pkomuda.utils.EmailSender;

import javax.persistence.PersistenceException;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccountServiceImpl implements AccountService {

    @Value("${host.url}")
    private String hostUrl;

    private final AccountRepository accountRepository;

    private final EmailSender emailSender;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void addAccount(Account account) throws ApplicationBaseException {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setActive(false);
        try {
            accountRepository.saveAndFlush(account);
        } catch (PersistenceException | DataAccessException e) {
            if (e.getMessage().contains("username_unique")
                    || e.getMessage().contains("email_unique")) {
                throw new AccountAlreadyExistsException(e);
            } else {
                throw new ApplicationBaseException(e);
            }
        }
        String text = "<a href=\"" + hostUrl + "/confirmAccount\">"
                + "Click here" + "</a>" + " to confirm your account";
        emailSender.sendMessage(account.getEmail(), "Confirm your account", text);
    }
}
