package com.xyzcompany.bookstore.transformer;

import org.springframework.stereotype.Component;

import com.xyzcompany.bookstore.domain.Book;
import com.xyzcompany.bookstore.dto.BookRequest;

@Component
public class BookTransformer {

    public Book transform(final BookRequest bookRequest) {
        final Book book = new Book();

        book.setIsbn(bookRequest.getIsbn());
        book.setName(bookRequest.getName());
        book.setAuthor(bookRequest.getAuthor());
        book.setCategories(bookRequest.getCategories());

        return book;
    }

    public void transform(final BookRequest source, final Book destination) {
        destination.setIsbn(source.getIsbn());
        destination.setName(source.getName());
        destination.setAuthor(source.getAuthor());
        destination.setCategories(source.getCategories());
    }
}
