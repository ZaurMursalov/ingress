package az.ingress.payment.service;

import az.ingress.payment.dto.PaymentDto;
import az.ingress.payment.dto.PaymentRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {

    PaymentDto createAndUpdate(PaymentRequestDto paymentRequestDto);

    PaymentDto getPayment(Long id);

    Page<PaymentDto> getAll(Pageable pageable);
    Page<PaymentDto> getAllPayments(Pageable pageable);
    List<PaymentDto> getPaymentsByPaymentDateBetween(LocalDateTime from, LocalDateTime to);

}
