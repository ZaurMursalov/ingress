package az.ingress.payment.service.impl;

import az.ingress.payment.domain.Balance;
import az.ingress.payment.domain.Account;
import az.ingress.payment.dto.BalanceDto;
import az.ingress.payment.repository.BalanceRepository;
import az.ingress.payment.repository.AccountRepository;
import az.ingress.payment.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public Double getCalculatePaymentBalance() {
        return getBalance().getPaymentBalance();
    }

    @Override
    public Double getCalculateExpenseBalance() {
        return getBalance().getExpenseBalance();
    }

    @Override
    public Double getCalculateTotalBalance() {
        return calculateTotalBalance(getBalance());
    }

    @Override
    public BalanceDto paymentManagersAndResetBalance() {
        Double calculateTotalBalance = getCalculateTotalBalance();
        increaseManagerBalance(calculateTotalBalance);
        resetBalance();
        return mapper.map(getBalance(),BalanceDto.class);
    }

    private void resetBalance() {
        Balance balance = getBalance();
        balance.setExpenseBalance(0.0);
        balance.setPaymentBalance(0.0);
        balanceRepository.save(balance);
    }

    private void increaseManagerBalance(Double balance) {
        Account account = accountRepository.getById(1L);
        Account account2 = accountRepository.getById(2L);
        accountRepository.save(account);
        accountRepository.save(account2);
    }

    private Balance getBalance() {
        try {
            return balanceRepository.findById(1L).get();
        } catch (NoSuchElementException exception) {
            return createDefaultBalance();
        }
    }

        private Balance createDefaultBalance () {
            Balance balance = new Balance();
            balance.setId(1L);
            balance.setPaymentBalance(0.0);
            balance.setExpenseBalance(0.0);
            balanceRepository.save(balance);
            return balance;
        }

    private Double calculateTotalBalance(Balance balance) {
        Double a = (balance.getPaymentBalance() - balance.getExpenseBalance()) / 2;
        return a;
        }
    }
