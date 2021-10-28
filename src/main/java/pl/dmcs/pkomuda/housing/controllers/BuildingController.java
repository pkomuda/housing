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
import pl.dmcs.pkomuda.housing.model.Building;
import pl.dmcs.pkomuda.housing.services.BuildingService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class BuildingController {

    private final BuildingService buildingService;

    @GetMapping("/addBuilding")
    public String addBuilding(Model model) {
        model.addAttribute("building", new Building());
        return "addBuilding";
    }

    @PostMapping("/addBuilding")
    public String addBuilding(@Valid @ModelAttribute("building") Building building,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addBuilding";
        }
        buildingService.addBuilding(building);
        return "redirect:/buildings";
    }

    @GetMapping("/building/{id}")
    public String getBuilding(@PathVariable Long id, Model model) throws ApplicationBaseException {
        model.addAttribute("building", buildingService.getBuilding(id));
        return "building";
    }

    @GetMapping("/buildings")
    public String getAllBuildings(Model model) {
        model.addAttribute("buildings", buildingService.getAllBuildings());
        return "buildings";
    }
}
