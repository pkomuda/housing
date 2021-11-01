package pl.dmcs.pkomuda.housing.services;

import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Flat;

import java.util.List;

public interface FlatService {

    void addFlat(Flat flat, Long buildingId) throws ApplicationBaseException;
    List<Flat> getAllFlats(Long buildingId);
    List<Flat> getEmptyFlats();
    void assignFlat(Long flatId, String username) throws ApplicationBaseException;
}
