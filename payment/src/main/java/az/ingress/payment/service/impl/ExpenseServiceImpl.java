package az.ingress.payment.service.impl;

import az.ingress.common.exception.ApplicationException;
import az.ingress.payment.domain.Balance;
import az.ingress.payment.domain.Expense;
import az.ingress.payment.dto.ExpenseDto;
import az.ingress.payment.errors.Errors;
import az.ingress.payment.repository.BalanceRepository;
import az.ingress.payment.repository.ExpenseRepository;
import az.ingress.payment.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BalanceRepository balanceRepository;
    private final ModelMapper mapper;

    @Override
    public ExpenseDto createAndUpdateExpense(ExpenseDto dto) {
        Expense expense = isUpdate(dto.getId()) ? updateExpense(dto) : createExpense(dto);
        return mapper.map(expenseRepository.save(expense), ExpenseDto.class);
    }

    @Override
    public ExpenseDto getExpense(Long id) {
        return mapper.map(fetchExpenseIfExist(id), ExpenseDto.class);

    }

    @Override
    public Page<ExpenseDto> getAll(Pageable pageable) {
        return expenseRepository.findAll(pageable)
                .map(expense -> mapper.map(expense, ExpenseDto.class));
    }

    private Expense fetchExpenseIfExist(Long id) {
        return expenseRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.EXPENSE_ID_NOT_FOUND, Map.of("id", id)));
    }

    private Balance getBalance() {
        return balanceRepository.findById(1L).get();
    }

    private void increaseTotalBalance(ExpenseDto dto) {
        Balance balance = getBalance();
        balance.setExpenseBalance(balance.getExpenseBalance() + dto.getAmount());
        balanceRepository.save(balance);
    }

    private void reduceTotalBalance(Double amount) {
        Balance balance = getBalance();
        balance.setExpenseBalance(balance.getExpenseBalance() - amount);
        balanceRepository.save(balance);
    }

    private void updateTotalBalance(ExpenseDto expenseDto) {
        ExpenseDto oldExpenseDto = getExpense(expenseDto.getId());
        Double updatedTotalBalance = oldExpenseDto.getAmount() - expenseDto.getAmount();
        reduceTotalBalance(updatedTotalBalance);
    }

    private boolean isUpdate(Long id) {
        return id != null;
    }

    private Expense updateExpense(ExpenseDto dto) {
        updateTotalBalance(dto);
        Expense expense = fetchExpenseIfExist(dto.getId());
        expense.setName(dto.getName());
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        return expense;
    }

    private Expense createExpense(ExpenseDto dto) {
        increaseTotalBalance(dto);
        return Expense.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .build();
    }
}

