package com.xyzcompany.bookstore.exception;

import static java.lang.String.format;

public class BookNotFoundException extends BaseException {

    private static final String MESSAGE = "Book [%s] has not been found";

    public BookNotFoundException(final Integer id) {
        super(ErrorCode.NOT_FOUND, getMessage(id));
    }

    private static String getMessage(final Integer id) {
        return format(MESSAGE, id);
    }
}
