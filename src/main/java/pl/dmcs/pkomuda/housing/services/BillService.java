package pl.dmcs.pkomuda.housing.services;

import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Bill;

import java.math.BigDecimal;
import java.util.Map;

public interface BillService {

    void addBill(Bill bill, Long flatId) throws ApplicationBaseException;
    Bill getBill(Long id) throws ApplicationBaseException;
    Map<Bill, BigDecimal> getAllBills(String username);
}
