package com.xyzcompany.bookstore.transformer;

import static com.xyzcompany.bookstore.util.BookTestUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.xyzcompany.bookstore.domain.Book;

class BookTransformerTest {

    private BookTransformer transformer = new BookTransformer();

    @Test
    void shouldTransformBookRequestToNewBook() {
        final Book book = transformer.transform(BOOK_REQUEST);

        assertThat(book, is(BOOK));
    }

    @Test
    void shouldTransformBookRequestToExistingBook() {
        final Book book = new Book();

        transformer.transform(BOOK_REQUEST, book);

        assertThat(book.getIsbn(), is(BOOK_REQUEST.getIsbn()));
        assertThat(book.getName(), is(BOOK_REQUEST.getName()));
        assertThat(book.getAuthor(), is(BOOK_REQUEST.getAuthor()));
        assertThat(book.getCategories(), is(BOOK_REQUEST.getCategories()));
    }
}