package az.ingress.payment.web.rest;

import az.ingress.payment.dto.CompanyRequestDto;
import az.ingress.payment.dto.CompanyResponseDto;
import az.ingress.payment.service.CompanyService;
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
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @PutMapping()
    public CompanyResponseDto createAndUpdate(@RequestBody CompanyRequestDto requestDto) {
        log.trace("Create and update company details {}", requestDto);
        return companyService.createAndUpdate(requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public Page<CompanyResponseDto> getAll(Pageable pageable) {
        log.trace("List all companies");
        return companyService.getAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CompanyResponseDto getManagerById(@PathVariable Long id) {
        log.trace("Get company by id {}", id);
        return companyService.getCompany(id);
    }
}
