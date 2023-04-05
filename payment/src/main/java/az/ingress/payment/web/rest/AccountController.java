package az.ingress.payment.web.rest;

import az.ingress.payment.dto.AccountDto;
import az.ingress.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAndUpdateAccount(@RequestBody AccountDto accountDto) {
        log.trace("Create and update account , {}", accountDto);
        return accountService.createAndUpdateAccount(accountDto);
    }

    @GetMapping()
    public AccountDto getAccountByUsername(@RequestParam String username) {
        log.trace("Get account by username , {}", username);
        return accountService.getAccountByUsername(username);
    }

    @GetMapping("/all")
    public Page<AccountDto> getAll(Pageable pageable) {
        log.trace("List all accounts");
        return accountService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public AccountDto getAccountById(@PathVariable Long id) {
        log.trace("Get account by id {}", id);
        return accountService.getAccount(id);
    }
}
