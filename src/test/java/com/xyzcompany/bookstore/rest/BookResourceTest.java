package com.xyzcompany.bookstore.rest;

import static com.xyzcompany.bookstore.util.BookTestUtil.*;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyzcompany.bookstore.dto.BookRequest;
import com.xyzcompany.bookstore.dto.ErrorResponse;
import com.xyzcompany.bookstore.exception.BaseException;
import com.xyzcompany.bookstore.exception.handler.GlobalExceptionHandler;
import com.xyzcompany.bookstore.service.BookService;

@ExtendWith(SpringExtension.class)
@WebMvcTest({ BookResource.class })
class BookResourceTest {

    private static final String CREATE_BOOK_URL = "/api/v1/books";
    private static final String UPDATE_BOOK_URL = "/api/v1/books/{id}";
    private static final String GET_BOOKS_URL = "/api/v1/books";
    private static final String GET_BOOK_URL = "/api/v1/books/{id}";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void shouldCreate() throws Exception {
        when(bookService.create(BOOK_REQUEST)).thenReturn(CREATED_BOOK);

        mvc.perform(post(CREATE_BOOK_URL).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BOOK_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", equalTo(CREATED_BOOK.getId())))
                .andExpect(jsonPath("isbn", equalTo(CREATED_BOOK.getIsbn())))
                .andExpect(jsonPath("name", equalTo(CREATED_BOOK.getName())))
                .andExpect(jsonPath("author", equalTo(CREATED_BOOK.getAuthor())))
                .andExpect(jsonPath("createdAt", equalTo(CREATED_BOOK.getCreatedAt()
                        .toString())))
                .andExpect(jsonPath("updatedAt", is(nullValue())))
                .andExpect(jsonPath("categories", equalTo(CREATED_BOOK.getCategories())));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenCreate() throws Exception {
        final ErrorResponse errorResponse = buildValidationFailedErrorResponse();
        when(exceptionHandler.handleMethodArgumentNotValidException(any()))
                .thenReturn(new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST));

        final BookRequest bookRequest = BOOK_REQUEST;

        bookRequest.setName(null);
        bookRequest.setIsbn(null);

        mvc.perform(post(CREATE_BOOK_URL).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code", equalTo(errorResponse.getCode())))
                .andExpect(jsonPath("httpStatusCode", equalTo(errorResponse.getHttpStatusCode())))
                .andExpect(jsonPath("message", equalTo(errorResponse.getMessage())));
    }

    @Test
    void shouldUpdate() throws Exception {
        when(bookService.update(ID, BOOK_REQUEST)).thenReturn(UPDATED_BOOK);

        mvc.perform(put(UPDATE_BOOK_URL, ID).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BOOK_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", equalTo(UPDATED_BOOK.getId())))
                .andExpect(jsonPath("isbn", equalTo(UPDATED_BOOK.getIsbn())))
                .andExpect(jsonPath("name", equalTo(UPDATED_BOOK.getName())))
                .andExpect(jsonPath("author", equalTo(UPDATED_BOOK.getAuthor())))
                .andExpect(jsonPath("createdAt", equalTo(UPDATED_BOOK.getCreatedAt()
                        .toString())))
                .andExpect(jsonPath("updatedAt", equalTo(UPDATED_BOOK.getUpdatedAt()
                        .toString())))
                .andExpect(jsonPath("categories", equalTo(UPDATED_BOOK.getCategories())));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUpdate() throws Exception {
        final ErrorResponse errorResponse = buildValidationFailedErrorResponse();
        when(exceptionHandler.handleMethodArgumentNotValidException(any()))
                .thenReturn(new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST));

        final BookRequest bookRequest = BOOK_REQUEST;

        bookRequest.setName(null);
        bookRequest.setIsbn(null);

        mvc.perform(put(UPDATE_BOOK_URL, ID).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code", equalTo(errorResponse.getCode())))
                .andExpect(jsonPath("httpStatusCode", equalTo(errorResponse.getHttpStatusCode())))
                .andExpect(jsonPath("message", equalTo(errorResponse.getMessage())));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdate() throws Exception {
        final ErrorResponse errorResponse = buildNotFoundErrorResponse();
        when(exceptionHandler.handleBaseException(any()))
                .thenReturn(new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND));
        when(bookService.update(NOT_EXISTING_ID, BOOK_REQUEST)).thenThrow(BaseException.class);

        mvc.perform(put(UPDATE_BOOK_URL, NOT_EXISTING_ID).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BOOK_REQUEST)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code", equalTo(errorResponse.getCode())))
                .andExpect(jsonPath("httpStatusCode", equalTo(errorResponse.getHttpStatusCode())))
                .andExpect(jsonPath("message", equalTo(errorResponse.getMessage())));
    }

    @Test
    void shouldGetBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(singletonList(CREATED_BOOK));

        mvc.perform(get(GET_BOOKS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(CREATED_BOOK.getId())))
                .andExpect(jsonPath("$.[*].isbn").value(hasItem(CREATED_BOOK.getIsbn())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(CREATED_BOOK.getName())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(CREATED_BOOK.getAuthor())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(CREATED_BOOK.getCreatedAt()
                        .toString())))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(nullValue())))
                .andExpect(jsonPath("$.[*].categories").value(hasItem(CREATED_BOOK.getCategories())));
    }

    @Test
    void shouldGetBook() throws Exception {
        when(bookService.findOneOrThrowException(ID)).thenReturn(CREATED_BOOK);

        mvc.perform(get(GET_BOOK_URL, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", equalTo(CREATED_BOOK.getId())))
                .andExpect(jsonPath("isbn", equalTo(CREATED_BOOK.getIsbn())))
                .andExpect(jsonPath("name", equalTo(CREATED_BOOK.getName())))
                .andExpect(jsonPath("author", equalTo(CREATED_BOOK.getAuthor())))
                .andExpect(jsonPath("createdAt", equalTo(CREATED_BOOK.getCreatedAt()
                        .toString())))
                .andExpect(jsonPath("updatedAt", is(nullValue())))
                .andExpect(jsonPath("categories", equalTo(CREATED_BOOK.getCategories())));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetBook() throws Exception {
        final ErrorResponse errorResponse = buildNotFoundErrorResponse();
        when(exceptionHandler.handleBaseException(any()))
                .thenReturn(new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND));
        when(bookService.findOneOrThrowException(NOT_EXISTING_ID)).thenThrow(BaseException.class);

        mvc.perform(get(GET_BOOK_URL, NOT_EXISTING_ID).contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(BOOK_REQUEST)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("code", equalTo(errorResponse.getCode())))
                .andExpect(jsonPath("httpStatusCode", equalTo(errorResponse.getHttpStatusCode())))
                .andExpect(jsonPath("message", equalTo(errorResponse.getMessage())));
    }

    private ErrorResponse buildValidationFailedErrorResponse() {
        final ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode("VALIDATION_FAILED");
        errorResponse.setHttpStatusCode("400");
        errorResponse.setMessage("[name] with message [must not be blank] and [isbn] with message [must not be blank]");

        return errorResponse;
    }

    private ErrorResponse buildNotFoundErrorResponse() {
        final ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode("OBJECT_NOT_FOUND");
        errorResponse.setHttpStatusCode("404");
        errorResponse.setMessage(format("Book [%s] has not been found", NOT_EXISTING_ID));

        return errorResponse;
    }
}