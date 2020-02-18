package com.xyzcompany.bookstore.exception;

public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    private String message;

    public BaseException(final ErrorCode errorCode, final String message) {
        super(message);
        this.message = message;
        this.errorCode = errorCode == null ? ErrorCode.INTERNAL_SERVER_ERROR : errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
