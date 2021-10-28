package pl.dmcs.pkomuda.housing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
public class AccountController {

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

    @GetMapping("/account/{username}")
    public String getAccount(@PathVariable String username, Model model) throws ApplicationBaseException {
        if (!model.containsAttribute("account")) {
            model.addAttribute("account", accountService.getAccount(username));
        }
        model.addAttribute("accessLevels", Arrays.stream(AccessLevelType.values())
                .map(AccessLevel::new)
                .toList());
        return "account";
    }

    @GetMapping("/account")
    public String getAccount(Authentication authentication, Model model) throws ApplicationBaseException {
        model.addAttribute(accountService.getAccount(authentication.getName()));
        return "account";
    }

    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts";
    }

    @PostMapping("/editAccount")
    public String editAccount(@ModelAttribute("account") Account account, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws ApplicationBaseException {
        accessLevelValidator.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.account", bindingResult);
            redirectAttributes.addFlashAttribute("account", account);
            return "redirect:account/" + account.getUsername();
        }
        accountService.editAccount(account);
        return "redirect:/accounts";
    }
}
