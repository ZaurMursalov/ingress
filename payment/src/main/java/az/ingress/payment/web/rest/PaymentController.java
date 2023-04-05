package az.ingress.payment.web.rest;

import az.ingress.payment.dto.PaymentDto;
import az.ingress.payment.dto.PaymentRequestDto;
import az.ingress.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto createAndUpdatePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        log.trace("Create and update payment , {}", paymentRequestDto);
        return paymentService.createAndUpdate(paymentRequestDto);
    }

//    @GetMapping("/all")
//    public Page<PaymentDto> getAll(Pageable pageable) {
//        log.trace("List all payments");
//        return paymentService.getAll(pageable);
//    }

    @GetMapping("/all")
    public Page<PaymentDto> getAllPayments(Pageable pageable){
        return paymentService.getAllPayments(pageable);
    }

    @GetMapping("/filter")
    public List<PaymentDto> getPaymentsByPaymentDateBetween(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {
        return paymentService.getPaymentsByPaymentDateBetween(from, to);
    }

    @GetMapping("/{id}")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        log.trace("Get payment by id {}", id);
        return paymentService.getPayment(id);
    }

}
