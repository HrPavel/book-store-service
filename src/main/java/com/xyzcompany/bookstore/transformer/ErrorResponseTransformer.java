package com.xyzcompany.bookstore.transformer;

import org.springframework.stereotype.Component;

import com.xyzcompany.bookstore.dto.ErrorResponse;
import com.xyzcompany.bookstore.exception.BaseException;
import com.xyzcompany.bookstore.exception.ErrorCode;

@Component
public class ErrorResponseTransformer {

    public ErrorResponse transform(final BaseException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(errorCode.getCode());
        errorResponse.setHttpStatusCode(String.valueOf(errorCode.getHttpStatus()
                .value()));
        errorResponse.setMessage(e.getMessage());

        return errorResponse;
    }
}
