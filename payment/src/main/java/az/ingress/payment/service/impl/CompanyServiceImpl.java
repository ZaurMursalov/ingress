package az.ingress.payment.service.impl;

import az.ingress.common.exception.ApplicationException;
import az.ingress.payment.domain.Company;
import az.ingress.payment.dto.CompanyRequestDto;
import az.ingress.payment.dto.CompanyResponseDto;
import az.ingress.payment.errors.Errors;
import az.ingress.payment.repository.CompanyRepository;
import az.ingress.payment.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {
    private static final Double COMPANY_DEFAULT_BALANCE = 0.00;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    public CompanyResponseDto createAndUpdate(CompanyRequestDto dto) {
        Company company = (isUpdate(dto.getId())) ? updateCompany(dto) : createCompany(dto);
        return mapper.map(companyRepository.save(company), CompanyResponseDto.class);
    }

    @Override
    public Page<CompanyResponseDto> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable).map(company ->
                mapper.map(company, CompanyResponseDto.class));
    }

    @Override
    public CompanyResponseDto getCompany(Long id) {
        return mapper.map(fetchCompanyIfExist(id), CompanyResponseDto.class);
    }

    private Company fetchCompanyIfExist(Long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.COMPANY_ID_NOT_FOUND, Map.of("id", id)));
    }

    private Company updateCompany(CompanyRequestDto dto) {
        Company company = fetchCompanyIfExist(dto.getId());
        company.setName(dto.getName());
        if (dto.getBalance() == null) {
            dto.setBalance(company.getBalance());
        }
        return company;
    }

    private Company createCompany(CompanyRequestDto dto) {
        return Company.builder()
                .name(dto.getName())
                .balance(COMPANY_DEFAULT_BALANCE)
                .build();
    }

    private boolean isUpdate(Long id) {
        return id != null;
    }
}
