package com.xyzcompany.bookstore.exception.handler;

import static java.lang.String.format;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.xyzcompany.bookstore.dto.ErrorResponse;
import com.xyzcompany.bookstore.exception.BaseException;
import com.xyzcompany.bookstore.exception.UnhandledException;
import com.xyzcompany.bookstore.exception.ValidationFailedException;
import com.xyzcompany.bookstore.transformer.ErrorResponseTransformer;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ErrorResponseTransformer transformer;

    public GlobalExceptionHandler(final ErrorResponseTransformer transformer) {
        this.transformer = transformer;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleNotHandledException(final Exception e) {
        log.error("Unhandled exception occurred", e);

        return getResponseEntityWithErrorResponse(new UnhandledException(e.getMessage()));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(final BaseException e) {
        log.error("Exception occurred: ", e);

        return getResponseEntityWithErrorResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final String fields = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> format("[%s] with message [%s]", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(" and "));

        return getResponseEntityWithErrorResponse(new ValidationFailedException(fields));
    }

    private ResponseEntity<ErrorResponse> getResponseEntityWithErrorResponse(final BaseException e) {
        return new ResponseEntity<>(transformer.transform(e), e.getErrorCode()
                .getHttpStatus());
    }
}
