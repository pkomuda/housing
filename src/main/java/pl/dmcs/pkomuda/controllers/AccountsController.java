package pl.dmcs.pkomuda.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dmcs.pkomuda.services.AccountService;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class AccountsController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public String getAllAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts";
    }
}
