package com.xyzcompany.bookstore.util;

import static java.util.Arrays.asList;

import java.time.Instant;
import java.util.List;

import com.xyzcompany.bookstore.domain.Book;
import com.xyzcompany.bookstore.dto.BookRequest;

public class BookTestUtil {

    public final static Integer ID = 1;
    public final static Integer NOT_EXISTING_ID = 2;

    private final static String ISBN = "0-0-0-0-0";
    private final static String NAME = "Test book";
    private final static String AUTHOR = "Test author";
    private final static List<String> CATEGORIES = asList("category_1", "category_2");

    public final static Book BOOK = buildBook();
    public final static Book CREATED_BOOK = buildCreatedBook();
    public final static Book UPDATED_BOOK = buildUpdatedBook();
    public final static BookRequest BOOK_REQUEST = buildBookRequest();

    private static BookRequest buildBookRequest() {
        final BookRequest bookRequest = new BookRequest();

        bookRequest.setIsbn(ISBN);
        bookRequest.setName(NAME);
        bookRequest.setAuthor(AUTHOR);
        bookRequest.setCategories(CATEGORIES);

        return bookRequest;
    }

    private static Book buildBook() {
        final Book book = new Book();

        book.setIsbn(ISBN);
        book.setName(NAME);
        book.setAuthor(AUTHOR);
        book.setCategories(CATEGORIES);

        return book;
    }

    private static Book buildCreatedBook() {
        final Book book = new Book();

        book.setId(ID);
        book.setIsbn(ISBN);
        book.setName(NAME);
        book.setAuthor(AUTHOR);
        book.setCategories(CATEGORIES);
        book.setCreatedAt(Instant.now());

        return book;
    }

    private static Book buildUpdatedBook() {
        final Book book = new Book();

        book.setId(ID);
        book.setIsbn(ISBN);
        book.setName(NAME);
        book.setAuthor(AUTHOR);
        book.setCategories(CATEGORIES);
        book.setCreatedAt(Instant.now());
        book.setUpdatedAt(Instant.now());

        return book;
    }
}
