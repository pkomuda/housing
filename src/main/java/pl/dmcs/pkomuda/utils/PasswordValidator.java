package pl.dmcs.pkomuda.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dmcs.pkomuda.model.Account;

@Component
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Account account = (Account) target;
        if (!account.getPassword().equals(account.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "error.account.password.match");
        }
    }
}
