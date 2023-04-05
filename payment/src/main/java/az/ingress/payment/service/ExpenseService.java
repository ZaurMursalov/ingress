package az.ingress.payment.service;


import az.ingress.payment.dto.ExpenseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    ExpenseDto createAndUpdateExpense(ExpenseDto expenseDto);

    ExpenseDto getExpense(Long id);

    Page<ExpenseDto> getAll(Pageable pageable);
}
