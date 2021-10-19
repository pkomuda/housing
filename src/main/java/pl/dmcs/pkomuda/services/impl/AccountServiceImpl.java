package pl.dmcs.pkomuda.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.dmcs.pkomuda.exceptions.AccountAlreadyExistsException;
import pl.dmcs.pkomuda.exceptions.AccountNotFoundException;
import pl.dmcs.pkomuda.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.model.Account;
import pl.dmcs.pkomuda.repositories.AccountRepository;
import pl.dmcs.pkomuda.services.AccountService;
import pl.dmcs.pkomuda.utils.EmailSender;

import javax.persistence.PersistenceException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ApplicationBaseException.class)
public class AccountServiceImpl implements AccountService {

    @Value("${host.url}")
    private String hostUrl;

    private final AccountRepository accountRepository;

    private final EmailSender emailSender;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void addAccount(Account account) throws ApplicationBaseException {
        String token = UUID.randomUUID().toString();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setPhoneNumber(StringUtils.trimAllWhitespace(account.getPhoneNumber()));
        account.setActive(false);
        account.setToken(token);
        try {
            accountRepository.saveAndFlush(account);
        } catch (PersistenceException | DataAccessException e) {
            if (e.getMessage().contains("username_unique")
                    || e.getMessage().contains("email_unique")) {
                throw new AccountAlreadyExistsException(e);
            }
            throw new ApplicationBaseException(e);
        }
        String text = "<a href=\"" + hostUrl + "/confirmAccount/" + token + "\">"
                + "Click here" + "</a>" + " to confirm your account";
        emailSender.sendMessage(account.getEmail(), "Confirm your account", text);
    }

    public void confirmAccount(String token) throws ApplicationBaseException {
        Optional<Account> accountOptional = accountRepository.findByToken(token);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException();
        }
        Account account = accountOptional.get();
        account.setActive(true);
    }
}
