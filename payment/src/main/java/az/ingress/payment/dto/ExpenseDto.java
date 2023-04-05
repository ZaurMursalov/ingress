package az.ingress.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ExpenseDto {
    Long id;

    String name;
    Double amount;
    String description;
}
