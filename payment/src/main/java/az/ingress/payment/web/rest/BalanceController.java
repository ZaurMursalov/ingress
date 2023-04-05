package az.ingress.payment.web.rest;

import az.ingress.payment.dto.BalanceDto;
import az.ingress.payment.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/paymentBalance")
    public Double getCalculatePaymentBalance() {
        log.trace("Calculating payment balance");
        return balanceService.getCalculatePaymentBalance();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/expenseBalance")
    public Double getCalculateExpenseBalance() {
        log.trace("Calculating Expense balance");
        return balanceService.getCalculateExpenseBalance();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalBalance")
    public Double getCalculateTotalBalance() {
        log.trace("Calculating total balance");
        return balanceService.getCalculateTotalBalance();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BalanceDto paymentManagersAndResetBalance() {
        log.trace("Paid to managers");
        return balanceService.paymentManagersAndResetBalance();
    }
}
