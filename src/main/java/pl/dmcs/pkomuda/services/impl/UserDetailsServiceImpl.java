package pl.dmcs.pkomuda.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.model.Account;
import pl.dmcs.pkomuda.repositories.AccountRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException(ApplicationBaseException.KEY_DEFAULT);
        }
        Account account = accountOptional.get();
        Set<GrantedAuthority> authorities = account.getAccessLevels().stream()
                .map(accessLevel -> new SimpleGrantedAuthority(accessLevel.getType().label))
                .collect(Collectors.toSet());
        return new User(account.getUsername(),
                account.getPassword(),
                account.getActive(),
                true,
                true,
                true,
                authorities);
    }
}
