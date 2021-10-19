package pl.dmcs.pkomuda.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.dmcs.pkomuda.exceptions.ApplicationBaseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable e, RedirectAttributes redirectAttributes) {
        if (e instanceof ApplicationBaseException) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:error";
    }
}
