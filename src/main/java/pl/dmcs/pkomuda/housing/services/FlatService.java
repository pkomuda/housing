package pl.dmcs.pkomuda.housing.services;

import pl.dmcs.pkomuda.housing.model.Flat;

import java.util.List;

public interface FlatService {

    void addFlat(Flat flat);
    List<Flat> getAllFlats(Long buildingId);
}
