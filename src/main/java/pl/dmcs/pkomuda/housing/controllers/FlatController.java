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

    @GetMapping("/addFlat/{buildingId}")
    public String addFlat(@PathVariable Long buildingId, Model model) {
        model.addAttribute("buildingId", buildingId);
        model.addAttribute("flat", new Flat());
        return "addFlat";
    }

    @PostMapping("/addFlat/{buildingId}")
    public String addFlat(@PathVariable Long buildingId, @Valid @ModelAttribute("flat") Flat flat,
                          BindingResult bindingResult) throws ApplicationBaseException {
        if (bindingResult.hasErrors()) {
            return "addFlat";
        }
        flat.setBuilding(buildingService.getBuilding(buildingId));
        flatService.addFlat(flat);
        return "redirect:/buildings";
    }

    @GetMapping("/flats/{buildingId}")
    public String getAllFlats(@PathVariable Long buildingId, Model model) {
        model.addAttribute("flats", flatService.getAllFlats(buildingId));
        return "flats";
    }
}
