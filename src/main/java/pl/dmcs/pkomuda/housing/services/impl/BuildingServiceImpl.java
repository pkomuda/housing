package pl.dmcs.pkomuda.housing.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.exceptions.BuildingNotFoundException;
import pl.dmcs.pkomuda.housing.model.Building;
import pl.dmcs.pkomuda.housing.repositories.BuildingRepository;
import pl.dmcs.pkomuda.housing.services.BuildingService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ApplicationBaseException.class)
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;

    @Override
    public void addBuilding(Building building) {
        building.setPostalCode(building.getPostalCode().replace("-", ""));
        buildingRepository.saveAndFlush(building);
    }

    @Override
    public Building getBuilding(Long id) throws ApplicationBaseException {
        return buildingRepository.findById(id)
                .orElseThrow(BuildingNotFoundException::new);
    }

    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.findAllByOrderByStreetNameAsc();
    }
}
