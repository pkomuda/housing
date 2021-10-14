package pl.dmcs.pkomuda.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.model.Account;
import pl.dmcs.pkomuda.repositories.AccountRepository;
import pl.dmcs.pkomuda.services.AccountService;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void addAccount(Account account) {
        accountRepository.saveAndFlush(account);
    }
}
