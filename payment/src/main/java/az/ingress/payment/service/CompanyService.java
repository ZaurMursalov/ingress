package az.ingress.payment.service;

import az.ingress.payment.dto.CompanyRequestDto;
import az.ingress.payment.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyResponseDto createAndUpdate(CompanyRequestDto requestDto);

    Page<CompanyResponseDto> getAll(Pageable pageable);

    CompanyResponseDto getCompany(Long id);
}
