package pl.dmcs.pkomuda.housing.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.dmcs.pkomuda.housing.exceptions.AccountAlreadyExistsException;
import pl.dmcs.pkomuda.housing.exceptions.AccountNotFoundException;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.AccessLevelType;
import pl.dmcs.pkomuda.housing.model.Account;
import pl.dmcs.pkomuda.housing.repositories.AccountRepository;
import pl.dmcs.pkomuda.housing.services.AccountService;
import pl.dmcs.pkomuda.housing.utils.EmailSender;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.ResourceBundle;
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
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setPhoneNumber(StringUtils.trimAllWhitespace(account.getPhoneNumber()));
        account.setConfirmed(true);
        account.setToken(UUID.randomUUID().toString());
        try {
            accountRepository.saveAndFlush(account);
        } catch (PersistenceException | DataAccessException e) {
            if (e.getMessage().contains("username_unique")
                    || e.getMessage().contains("email_unique")) {
                throw new AccountAlreadyExistsException(e);
            }
            throw new ApplicationBaseException(e);
        }
    }

    @Override
    public void register(Account account) throws ApplicationBaseException {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setPhoneNumber(StringUtils.trimAllWhitespace(account.getPhoneNumber()));
        account.setActive(true);
        account.setConfirmed(false);
        account.setToken(UUID.randomUUID().toString());
        account.addAccessLevel(AccessLevelType.RESIDENT);
        try {
            accountRepository.saveAndFlush(account);
        } catch (PersistenceException | DataAccessException e) {
            if (e.getMessage().contains("username_unique")
                    || e.getMessage().contains("email_unique")) {
                throw new AccountAlreadyExistsException(e);
            }
            throw new ApplicationBaseException(e);
        }
        sendConfirmationEmail(account.getEmail(), account.getToken());
    }

    @Override
    public void confirmAccount(String token) throws ApplicationBaseException {
        Account account = accountRepository.findByToken(token)
                .orElseThrow(AccountNotFoundException::new);
        account.setConfirmed(true);
    }

    @Override
    public Account getAccount(String username) throws ApplicationBaseException {
        return accountRepository.findByUsername(username)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAllByOrderByUsernameAsc();
    }

    @Override
    public void editAccount(Account account) throws AccountNotFoundException {
        Account temp = accountRepository.findByUsername(account.getUsername())
                .orElseThrow(AccountNotFoundException::new);
        temp.setActive(account.getActive());
        temp.setAccessLevels(account.getAccessLevels());
    }

    private void sendConfirmationEmail(String email, String token) throws ApplicationBaseException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        String subject = resourceBundle.getString("email.confirmAccount.subject");
        String text = "<a href=\"" + hostUrl + "/confirmAccount/" + token + "\">"
                + resourceBundle.getString("email.confirmAccount.link") + "</a> "
                + resourceBundle.getString("email.confirmAccount.text");
        emailSender.sendMessage(email, subject, text);
    }
}
