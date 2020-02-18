package com.xyzcompany.bookstore.service;

import static com.xyzcompany.bookstore.util.BookTestUtil.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xyzcompany.bookstore.domain.Book;
import com.xyzcompany.bookstore.exception.BookNotFoundException;
import com.xyzcompany.bookstore.repository.BookRepository;
import com.xyzcompany.bookstore.transformer.BookTransformer;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @Mock
    private BookTransformer transformer;

    @InjectMocks
    private BookService service;

    @Test
    void shouldCreate() {
        when(transformer.transform(BOOK_REQUEST)).thenReturn(BOOK);
        when(repository.save(BOOK)).thenReturn(CREATED_BOOK);

        final Book book = service.create(BOOK_REQUEST);

        verify(repository).save(BOOK);
        assertThat(book, is(CREATED_BOOK));
    }

    @Test
    void shouldUpdate() {
        when(repository.findById(ID)).thenReturn(Optional.of(CREATED_BOOK));
        when(repository.save(CREATED_BOOK)).thenReturn(UPDATED_BOOK);

        final Book book = service.update(ID, BOOK_REQUEST);

        verify(repository).save(CREATED_BOOK);
        assertThat(book, is(UPDATED_BOOK));
    }

    @Test
    void shouldGetOneBook() {
        when(repository.findById(ID)).thenReturn(Optional.of(CREATED_BOOK));

        final Book book = service.findOneOrThrowException(ID);

        verify(repository).findById(ID);
        assertThat(book, is(CREATED_BOOK));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetOneBook() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> service.findOneOrThrowException(ID));
    }

    @Test
    void shouldGetBooks() {
        when(repository.findAll()).thenReturn(singletonList(CREATED_BOOK));

        final List<Book> books = service.getBooks();

        verify(repository).findAll();
        assertThat(books, hasSize(1));
        assertThat(books, contains(CREATED_BOOK));
    }

    @Test
    void shouldGetEmptyBookListO() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        final List<Book> books = service.getBooks();

        verify(repository).findAll();
        assertThat(books, hasSize(0));
    }
}