package pl.dmcs.pkomuda.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.dmcs.pkomuda.model.Account;
import pl.dmcs.pkomuda.services.AccountService;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("account") Account account) {
        accountService.addAccount(account);
        return "redirect:/";
    }
}
