package az.ingress.payment.errors;

import az.ingress.common.exception.ErrorResponse;
import az.ingress.common.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse {

    COURSE_ID_NOT_FOUND("COURSE_ID_NOT_FOUND", HttpStatus.NOT_FOUND, "Course id {id} not found!"),
    ACCOUNT_ID_NOT_FOUND("ACCOUNT_ID_NOT_FOUND", HttpStatus.NOT_FOUND, "Account id {id} not found!"),
    ACCOUNT_NOT_FOUND_WITH_USERNAME("ACCOUNT_NOT_FOUND_WITH_USERNAME", HttpStatus.NOT_FOUND,
            "Account not found with username: {username}"),
    PAYMENT_ID_NOT_FOUND("PAYMENT_ID_NOT_FOUND", HttpStatus.NOT_FOUND, "Payment id {id} not found!"),
    USERNAME_ALREADY_HAVE("USERNAME_ALREADY_HAVE", HttpStatus.NOT_FOUND,
            "Username {username} already have!"),
    EXPENSE_ID_NOT_FOUND("EXPENSE_ID_NOT_FOUND", HttpStatus.NOT_FOUND, "Expense id {id} not found!"),
    COMPANY_ID_NOT_FOUND("COMPANY_ID_NOT_FOUND", HttpStatus.NOT_FOUND, "Company id {id} not found!");

    String key;
    HttpStatus httpStatus;
    String message;

    Errors(String key, HttpStatus httpStatus, String message) {
        this.message = message;
        this.key = key;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
