package az.ingress.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {
    Long id;

    Double amount;
    AccountDto account;
    CourseDto course;
    CompanyRequestDto company;
    LocalDateTime receiptPaymentDate;
    LocalDateTime paymentDate;
    Long coursePaymentMonth;
    //Receipt

}
