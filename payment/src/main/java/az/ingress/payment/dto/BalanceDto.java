package az.ingress.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BalanceDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double paymentBalance;
    Double expenseBalance;
}
