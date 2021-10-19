package pl.dmcs.pkomuda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.dmcs.pkomuda.exceptions.ApplicationBaseException;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String error(Model model) {
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", ApplicationBaseException.KEY_DEFAULT);
        }
        return "error";
    }
}
