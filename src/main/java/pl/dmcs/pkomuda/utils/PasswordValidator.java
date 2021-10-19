package pl.dmcs.pkomuda.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.dmcs.pkomuda.model.Account;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Account account = (Account) target;
        if (!Pattern.compile("[A-ZĄĆĘŁŃÓŚŹŻ]+").matcher(account.getPassword()).find()
                || !Pattern.compile("[a-ząćęłńóśźż]+").matcher(account.getPassword()).find()
                || !Pattern.compile("[0-9]+").matcher(account.getPassword()).find()
                || !Pattern.compile("[!@#$%^&*()]+").matcher(account.getPassword()).find()) {
            errors.rejectValue("password", "error.account.password.strength");
        }
        if (!account.getPassword().equals(account.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "error.account.password.match");
        }
    }
}
