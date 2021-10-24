package pl.dmcs.pkomuda.housing.controllers;

import lombok.RequiredArgsConstructor;
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
import pl.dmcs.pkomuda.housing.model.Account;
import pl.dmcs.pkomuda.housing.services.AccountService;
import pl.dmcs.pkomuda.housing.utils.PasswordValidator;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class RegisterController {

    private final AccountService accountService;

    private final PasswordValidator passwordValidator;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") Account account,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws ApplicationBaseException {
        passwordValidator.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        accountService.register(account);
        redirectAttributes.addFlashAttribute("registrationSuccess", true);
        return "redirect:/";
    }

    @GetMapping("/confirmAccount/{token}")
    public String confirmAccount(@PathVariable String token) throws ApplicationBaseException {
        accountService.confirmAccount(token);
        return "confirmAccount";
    }
}
