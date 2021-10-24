package pl.dmcs.pkomuda.housing.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dmcs.pkomuda.housing.model.Account;

@Component
public class AccessLevelValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Account account = (Account) target;
        if (account.getAccessLevels().isEmpty()) {
            errors.rejectValue("accessLevels", "error.account.accessLevels.required");
        }
    }
}
