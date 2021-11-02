package pl.dmcs.pkomuda.housing.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.dmcs.pkomuda.housing.exceptions.AccountNotFoundException;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.Account;
import pl.dmcs.pkomuda.housing.model.Bill;
import pl.dmcs.pkomuda.housing.model.Utility;
import pl.dmcs.pkomuda.housing.repositories.AccountRepository;
import pl.dmcs.pkomuda.housing.repositories.BillRepository;
import pl.dmcs.pkomuda.housing.services.BillService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ApplicationBaseException.class)
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    private final AccountRepository accountRepository;

    @Override
    public void addBill(Bill bill, Long flatId) throws ApplicationBaseException {
        for (Utility utility : bill.getUtilities()) {
            utility.setBill(bill);
        }
        Account account = accountRepository.findByFlatId(flatId)
                .orElseThrow(AccountNotFoundException::new);
        account.addBill(bill);
    }

    @Override
    public Map<Bill, BigDecimal> getAllBills(String username) {
        return billRepository.findAllByAccountUsernameOrderByIssueDateDesc(username).stream()
                .collect(Collectors.toMap(Function.identity(), bill -> bill.getUtilities().stream()
                        .map(Utility::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
