package pl.dmcs.pkomuda.housing.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Flat;
import pl.dmcs.pkomuda.housing.repositories.FlatRepository;
import pl.dmcs.pkomuda.housing.services.FlatService;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ApplicationBaseException.class)
public class FlatServiceImpl implements FlatService {

    private final FlatRepository flatRepository;

    @Override
    public void addFlat(Flat flat) {
        flatRepository.saveAndFlush(flat);
    }
}
