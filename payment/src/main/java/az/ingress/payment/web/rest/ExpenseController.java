package az.ingress.payment.web.rest;

import az.ingress.payment.dto.ExpenseDto;
import az.ingress.payment.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseDto createAndUpdateExpense(@RequestBody ExpenseDto expenseDto) {
        log.trace("Create and update expense , {}", expenseDto);
        return expenseService.createAndUpdateExpense(expenseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public Page<ExpenseDto> getAll(Pageable pageable) {
        log.trace("List all expense");
        return expenseService.getAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ExpenseDto getExpenseById(@PathVariable Long id) {
        log.trace("Get expense by id {}", id);
        return expenseService.getExpense(id);
    }
}
