package az.ingress.payment.repository;

import az.ingress.payment.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> getAccountByUsername(String username);
    Boolean existsAccountByUsername(String username);

}