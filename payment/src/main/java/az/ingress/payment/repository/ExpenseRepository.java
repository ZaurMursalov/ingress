package az.ingress.payment.repository;

import az.ingress.payment.domain.Expense;
import az.ingress.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
