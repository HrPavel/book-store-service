package com.xyzcompany.bookstore.transformer;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.xyzcompany.bookstore.dto.ErrorResponse;
import com.xyzcompany.bookstore.exception.BaseException;
import com.xyzcompany.bookstore.exception.BookNotFoundException;

class ErrorResponseTransformerTest {

    private final static Integer ID = 1;

    private ErrorResponseTransformer transformer = new ErrorResponseTransformer();

    @Test
    void shouldTransform() {
        final ErrorResponse errorResponse = transformer.transform(buildNotFoundException());

        assertThat(errorResponse, is(buildNotFoundErrorResponse()));
    }

    private BaseException buildNotFoundException() {
        return new BookNotFoundException(ID);
    }

    private ErrorResponse buildNotFoundErrorResponse() {
        final ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setHttpStatusCode("404");
        errorResponse.setCode("OBJECT_NOT_FOUND");
        errorResponse.setMessage(format("Book [%s] has not been found", ID));

        return errorResponse;
    }
}