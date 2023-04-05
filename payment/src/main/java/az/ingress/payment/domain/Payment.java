package az.ingress.payment.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double amount;

    Long coursePaymentMonth;

    @CreationTimestamp
    LocalDateTime paymentDate;

    LocalDateTime receiptPaymentDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    Course course;

    @OneToOne(cascade = CascadeType.PERSIST)
    Company company;

    @OneToOne(cascade = CascadeType.PERSIST)
    Account account;

}
