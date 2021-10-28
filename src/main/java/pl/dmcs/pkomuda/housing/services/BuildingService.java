package pl.dmcs.pkomuda.housing.services;

import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Building;

import java.util.List;

public interface BuildingService {

    void addBuilding(Building building);
    Building getBuilding(Long id) throws ApplicationBaseException;
    List<Building> getAllBuildings();
}
