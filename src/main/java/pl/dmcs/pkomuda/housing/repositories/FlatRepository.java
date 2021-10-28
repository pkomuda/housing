package pl.dmcs.pkomuda.housing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Flat;

@Repository
@Transactional(propagation = Propagation.MANDATORY, rollbackFor = ApplicationBaseException.class)
public interface FlatRepository extends JpaRepository<Flat, Long> {

}
