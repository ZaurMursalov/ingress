package az.ingress.payment.service;


import az.ingress.payment.dto.BalanceDto;

public interface BalanceService {
    Double getCalculatePaymentBalance();

    Double getCalculateExpenseBalance();

    Double getCalculateTotalBalance();

    BalanceDto paymentManagersAndResetBalance();
}