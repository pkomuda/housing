package pl.dmcs.pkomuda.housing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import pl.dmcs.pkomuda.housing.utils.CaptchaValidator;
import pl.dmcs.pkomuda.housing.utils.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class RegisterController {

    @Value("${captcha.key.site}")
    private String captchaSiteKey;

    private final AccountService accountService;

    private final PasswordValidator passwordValidator;

    private final CaptchaValidator captchaValidator;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("captchaSiteKey", captchaSiteKey);
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") Account account,
                           BindingResult bindingResult, Model model,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) throws ApplicationBaseException {
        passwordValidator.validate(account, bindingResult);
        if (bindingResult.hasErrors()
                || !captchaValidator.validate(request.getParameter("g-recaptcha-response"))) {
            model.addAttribute("captchaSiteKey", captchaSiteKey);
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
