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
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Flat;
import pl.dmcs.pkomuda.housing.services.BuildingService;
import pl.dmcs.pkomuda.housing.services.FlatService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class FlatController {

    private final FlatService flatService;

    private final BuildingService buildingService;

    @GetMapping("/addFlat/{id}")
    public String addFlat(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("flat", new Flat());
        return "addFlat";
    }

    @PostMapping("/addFlat/{id}")
    public String addFlat(@PathVariable Long id, @Valid @ModelAttribute("flat") Flat flat,
                          BindingResult bindingResult) throws ApplicationBaseException {
        if (bindingResult.hasErrors()) {
            return "addFlat";
        }
        flat.setBuilding(buildingService.getBuilding(id));
        flatService.addFlat(flat);
        return "redirect:/buildings";
    }
}
