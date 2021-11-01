package pl.dmcs.pkomuda.housing.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.housing.exceptions.AccountNotFoundException;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.exceptions.BuildingNotFoundException;
import pl.dmcs.pkomuda.housing.exceptions.FlatNotFoundException;
import pl.dmcs.pkomuda.housing.model.Flat;
import pl.dmcs.pkomuda.housing.repositories.AccountRepository;
import pl.dmcs.pkomuda.housing.repositories.BuildingRepository;
import pl.dmcs.pkomuda.housing.repositories.FlatRepository;
import pl.dmcs.pkomuda.housing.services.FlatService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ApplicationBaseException.class)
public class FlatServiceImpl implements FlatService {

    private final FlatRepository flatRepository;

    private final BuildingRepository buildingRepository;

    private final AccountRepository accountRepository;

    @Override
    public void addFlat(Flat flat, Long buildingId) throws ApplicationBaseException {
        flat.setBuilding(buildingRepository.findById(buildingId)
                .orElseThrow(BuildingNotFoundException::new));
        flatRepository.saveAndFlush(flat);
    }

    @Override
    public List<Flat> getAllFlats(Long buildingId) {
        return flatRepository.findAllByBuildingIdOrderByNumberAsc(buildingId);
    }

    @Override
    public List<Flat> getEmptyFlats() {
        return flatRepository.findAllByAccountIsNullOrderByBuildingStreetNameAsc();
    }

    @Override
    public void assignFlat(Long flatId, String username) throws ApplicationBaseException {
        Flat flat = flatRepository.findById(flatId)
                .orElseThrow(FlatNotFoundException::new);
        flat.setAccount(accountRepository.findByUsername(username)
                .orElseThrow(AccountNotFoundException::new));
    }
}
