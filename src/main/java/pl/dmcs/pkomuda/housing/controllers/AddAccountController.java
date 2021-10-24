package pl.dmcs.pkomuda.housing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.AccessLevel;
import pl.dmcs.pkomuda.housing.model.AccessLevelType;
import pl.dmcs.pkomuda.housing.model.Account;
import pl.dmcs.pkomuda.housing.services.AccountService;
import pl.dmcs.pkomuda.housing.utils.AccessLevelValidator;
import pl.dmcs.pkomuda.housing.utils.PasswordValidator;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class AddAccountController {

    private final AccountService accountService;

    private final AccessLevelValidator accessLevelValidator;

    private final PasswordValidator passwordValidator;

    @GetMapping("/addAccount")
    public String addAccount(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("accessLevels", Arrays.stream(AccessLevelType.values())
                .map(AccessLevel::new)
                .toList());
        return "addAccount";
    }

    @PostMapping("/addAccount")
    public String addAccount(@Valid @ModelAttribute("account") Account account,
                             BindingResult bindingResult, Model model) throws ApplicationBaseException {
        accessLevelValidator.validate(account, bindingResult);
        passwordValidator.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("accessLevels", Arrays.stream(AccessLevelType.values())
                    .map(AccessLevel::new)
                    .toList());
            return "addAccount";
        }
        accountService.addAccount(account);
        return "redirect:/accounts";
    }
}
