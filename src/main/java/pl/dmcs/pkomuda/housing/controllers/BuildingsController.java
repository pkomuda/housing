package pl.dmcs.pkomuda.housing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dmcs.pkomuda.housing.model.Building;
import pl.dmcs.pkomuda.housing.services.BuildingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class BuildingsController {

    private final BuildingService buildingService;

    @GetMapping("/api/buildings")
    @PreAuthorize("hasAuthority(T(pl.dmcs.pkomuda.housing.model.AccessLevelType).MANAGER.label)")
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }
}
