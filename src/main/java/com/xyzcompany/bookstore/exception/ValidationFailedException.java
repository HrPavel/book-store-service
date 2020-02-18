package com.xyzcompany.bookstore.exception;

public class ValidationFailedException extends BaseException {

    public ValidationFailedException(final String message) {
        super(ErrorCode.REQUEST_VALIDATION, message);
    }
}
