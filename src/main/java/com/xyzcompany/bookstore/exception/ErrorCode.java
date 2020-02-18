package com.xyzcompany.bookstore.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_FOUND("OBJECT_NOT_FOUND", HttpStatus.NOT_FOUND),
    REQUEST_VALIDATION("VALIDATION_FAILED", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("UNHANDLED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    private String code;
    private HttpStatus httpStatus;

    ErrorCode(final String code, final HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
