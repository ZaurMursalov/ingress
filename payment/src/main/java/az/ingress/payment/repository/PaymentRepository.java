package az.ingress.payment.repository;

import az.ingress.payment.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    List<Payment> getPaymentsByPaymentDateBetween(LocalDateTime from, LocalDateTime to);
}
