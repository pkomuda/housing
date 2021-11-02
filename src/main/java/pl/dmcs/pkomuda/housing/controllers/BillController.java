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
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Bill;
import pl.dmcs.pkomuda.housing.model.Utility;
import pl.dmcs.pkomuda.housing.model.UtilityType;
import pl.dmcs.pkomuda.housing.services.BillService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class BillController {

    private final BillService billService;

    @GetMapping("/addBill/{flatId}")
    public String addBill(@PathVariable Long flatId, Model model) {
        model.addAttribute("flatId", flatId);
        model.addAttribute("bill", Bill.builder()
                .issueDate(LocalDateTime.now())
                .utilities(Arrays.stream(UtilityType.values())
                        .map(Utility::new)
                        .toList())
                .build());
        return "addBill";
    }

    @PostMapping("/addBill/{flatId}")
    public String addBill(@PathVariable Long flatId, @Valid @ModelAttribute("bill") Bill bill,
                          BindingResult bindingResult) throws ApplicationBaseException {
        if (bindingResult.hasErrors()) {
            return "addBill";
        }
        billService.addBill(bill, flatId);
        return "redirect:/buildings";
    }

    @GetMapping("/bill/{id}")
    public String getBill(@PathVariable Long id, Model model) throws ApplicationBaseException {
        model.addAttribute("bill", billService.getBill(id));
        return "bill";
    }

    @GetMapping("/bills")
    public String getAllBills(Authentication authentication, Model model) {
        model.addAttribute("bills", billService.getAllBills(authentication.getName()));
        return "bills";
    }
}
