package az.ingress.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequestDto {
    Long id;
    Double amount;
    Long accountId;
    Long courseId;
    Long companyId;
    LocalDateTime receiptPaymentDate;
    LocalDateTime paymentDate;
    Long coursePaymentMonth;
}
