package az.ingress.payment.service.impl;

import az.ingress.common.exception.ApplicationException;
import az.ingress.payment.domain.Account;
import az.ingress.payment.dto.AccountDto;
import az.ingress.payment.errors.Errors;
import az.ingress.payment.repository.AccountRepository;
import az.ingress.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Override
    public AccountDto createAndUpdateAccount(AccountDto dto) {
        Account account = isUpdate(dto.getId()) ? updateAccount(dto) : createAccount(dto);
        return mapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public Page<AccountDto> getAll(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(account -> mapper.map(account, AccountDto.class));
    }

    @Override
    public AccountDto getAccount(Long id) {
        return mapper.map(fetchAccountIfExist(id), AccountDto.class);
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        return mapper.map(fetchUsernameIfExist(username), AccountDto.class);
    }

    private Account fetchUsernameIfExist(String username) {
        return accountRepository.getAccountByUsername(username).orElseThrow(() ->
                new ApplicationException(Errors.ACCOUNT_NOT_FOUND_WITH_USERNAME, Map.of("username", username)));
    }

    private Account fetchAccountIfExist(Long id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.ACCOUNT_ID_NOT_FOUND, Map.of("id", id)));
    }

    private Account updateAccount(AccountDto dto) {
        Account account = fetchAccountIfExist(dto.getId());
        account.setUsername(dto.getUsername());
        account.setName(dto.getName());
        account.setSurname(dto.getSurname());
        return account;
    }

    private Account createAccount(AccountDto dto) {
        isExistsUsername(dto.getUsername());
        return Account.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .username(dto.getUsername())
                .build();
    }

    private boolean isUpdate(Long id) {
        return id != null;
    }

    private void isExistsUsername(String username) {
        Boolean isExists = accountRepository.existsAccountByUsername(username);
        if (isExists)
            throw new ApplicationException(Errors.USERNAME_ALREADY_HAVE, Map.of("username", username));
    }

}
