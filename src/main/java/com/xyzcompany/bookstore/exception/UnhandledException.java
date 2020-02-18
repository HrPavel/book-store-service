package com.xyzcompany.bookstore.exception;

public class UnhandledException extends BaseException {

    public UnhandledException(final String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}
