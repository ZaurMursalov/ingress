package az.ingress.payment.service.impl;

import az.ingress.common.exception.ApplicationException;
import az.ingress.payment.domain.*;
import az.ingress.payment.dto.*;
import az.ingress.payment.errors.Errors;
import az.ingress.payment.repository.*;
import az.ingress.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final BalanceRepository balanceRepository;
    private final ModelMapper mapper;

    @Override
    public PaymentDto createAndUpdate(PaymentRequestDto dto) {
        Payment payment = isUpdate(dto.getId()) ? updatePayment(dto) : createPayment(dto);
        return paymentEntityToDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentDto getPayment(Long id) {
        return mapper.map(fetchPaymentIfExist(id), PaymentDto.class);
    }

    @Override
    public Page<PaymentDto> getAll(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(payment -> mapper.map(payment, PaymentDto.class));
    }

    @Override
    public Page<PaymentDto> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(payment ->
                mapper.map(payment, PaymentDto.class));
    }

    @Override
    public List<PaymentDto> getPaymentsByPaymentDateBetween(LocalDateTime from, LocalDateTime to) {
        return paymentRepository.getPaymentsByPaymentDateBetween(from, to)
                .stream()
                .map(entity -> mapper.map(entity, PaymentDto.class))
                .collect(Collectors.toList());
    }

    private Payment fetchPaymentIfExist(Long id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.PAYMENT_ID_NOT_FOUND, Map.of("id", id)));
    }

    private PaymentDto paymentEntityToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setCoursePaymentMonth(payment.getCoursePaymentMonth());
        paymentDto.setAccount(mapper.map(payment.getAccount(), AccountDto.class));
        paymentDto.setCompany(mapper.map(payment.getCompany(), CompanyRequestDto.class));
        paymentDto.setCourse(mapper.map(payment.getCourse(), CourseDto.class));
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPaymentDate(LocalDateTime.now());
        return paymentDto;
    }

    private Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.ACCOUNT_ID_NOT_FOUND, Map.of("id", id)));
    }

    private Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(createDefaultCompany());
    }

    private Company createDefaultCompany() {
        Company ingress_academy = Company.builder()
                .balance(0.0)
                .id(1L)
                .name("Ingress Academy")
                .build();
        companyRepository.save(ingress_academy);
        return ingress_academy;
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.COURSE_ID_NOT_FOUND, Map.of("id", id)));
    }

    private Payment paymentDtoToEntity(PaymentRequestDto dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setCompany(getCompanyById(dto.getId()));
        payment.setAccount(getAccountById(dto.getId()));
//        payment.setCourse(getCourseById(dto.getId()));
        payment.setCoursePaymentMonth(dto.getCoursePaymentMonth());
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(dto.getPaymentDate());
        return payment;
    }

    private Balance getBalance() {
        try {
            return balanceRepository.findById(1L).get();
        } catch (NoSuchElementException exception) {
            return createDefaultBalance();
        }
    }

    private Balance createDefaultBalance() {
        Balance balance = new Balance();
        balance.setId(1L);
        balance.setPaymentBalance(0.0);
        balance.setExpenseBalance(0.0);
        balanceRepository.save(balance);
        return balance;
    }

    private void increaseCompanyBalance(PaymentRequestDto dto) {
        Company company = getCompanyById(dto.getId());
        company.setBalance(company.getBalance() + dto.getAmount());
        companyRepository.save(company);
    }

    private void increasePaymentBalance(Double amount) {
        Balance balance = getBalance();
        balance.setPaymentBalance(balance.getPaymentBalance() + amount);
        balanceRepository.save(balance);
    }

    private void updatePaymentBalance(PaymentRequestDto paymentRequestDto) {
        PaymentDto oldPaymentDto = getPayment(paymentRequestDto.getId());
        Double updatedPaymentBalance = oldPaymentDto.getAmount() - paymentRequestDto.getAmount();
        reducePaymentBalance(updatedPaymentBalance);
    }

    private void updateCompanyBalance(PaymentRequestDto paymentRequestDto) {
        PaymentDto oldPaymentDto = getPayment(paymentRequestDto.getId());
        Double updatedPaymentBalance = oldPaymentDto.getAmount() - paymentRequestDto.getAmount();
        reduceCompanyBalance(updatedPaymentBalance);
    }

    private void reduceCompanyBalance(Double amount) {
        Company company = getCompanyById(1L);
        company.setBalance(company.getBalance() - amount);
        companyRepository.save(company);
    }

    private void reducePaymentBalance(Double amount) {
        Balance balance = getBalance();
        balance.setPaymentBalance(balance.getPaymentBalance() - amount);
        balanceRepository.save(balance);
    }

    private boolean isUpdate(Long id) {
        return id != null;
    }

    private Payment createPayment(PaymentRequestDto dto) {
        increasePaymentBalance(dto.getAmount());
        increaseCompanyBalance(dto);
        return paymentDtoToEntity(dto);
    }

    private Payment updatePayment(PaymentRequestDto dto) {
        Payment payment = fetchPaymentIfExist(dto.getId());
        updatePaymentBalance(dto);
        updateCompanyBalance(dto);
        return payment;
    }
}
