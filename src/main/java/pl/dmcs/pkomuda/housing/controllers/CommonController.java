package pl.dmcs.pkomuda.housing.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;

import java.util.List;

@Controller
@SessionAttributes("accessLevel")
public class CommonController {

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        if (authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && !model.containsAttribute("accessLevel")) {
            model.addAttribute("accessLevel", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(""));
        }
        if (!model.containsAttribute("registrationSuccess")) {
            model.addAttribute("registrationSuccess", false);
        }
        return "index";
    }

    @GetMapping("/changeAccessLevel/{value}")
    public String changeAccessLevel(@PathVariable String value,
                                    Model model, Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        if (authorities.contains(value)) {
            model.addAttribute("accessLevel", value);
        }
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error(Model model) {
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", ApplicationBaseException.KEY_DEFAULT);
        }
        return "error";
    }
}
