package br.com.tgid.tgidtransaction.exception;

import br.com.tgid.tgidtransaction.model.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class StandardExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<StandardError> customerAlreadyExistsException(CustomerAlreadyExistsException e, HttpServletRequest request) {
        String error = "Unable to create customer.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<StandardError> companyAlreadyExistsException(CompanyAlreadyExistsException e, HttpServletRequest request) {
        String error = "Unable to create company.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<StandardError> customerNotFoundException(CustomerNotFoundException e, HttpServletRequest request) {
        String error = "Unable to update or delete this customer.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<StandardError> companyNotFoundException(CompanyNotFoundException e, HttpServletRequest request) {
        String error = "Unable to update or delete this customer.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<StandardError> transactionException(TransactionException e, HttpServletRequest request) {
        String error = "Unable to continue this transaction.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
