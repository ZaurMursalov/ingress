package az.ingress.payment.service;

import az.ingress.payment.dto.AccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AccountDto createAndUpdateAccount (AccountDto dto);

    Page<AccountDto> getAll(Pageable pageable);

    AccountDto getAccount(Long id);

    AccountDto getAccountByUsername(String username);
}
