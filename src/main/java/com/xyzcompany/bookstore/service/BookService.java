package com.xyzcompany.bookstore.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xyzcompany.bookstore.domain.Book;
import com.xyzcompany.bookstore.dto.BookRequest;
import com.xyzcompany.bookstore.repository.BookRepository;
import com.xyzcompany.bookstore.transformer.BookTransformer;

@Service
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private BookRepository repository;
    private BookTransformer transformer;

    public BookService(final BookRepository repository, final BookTransformer transformer) {
        this.repository = repository;
        this.transformer = transformer;
    }

    public Book create(final BookRequest bookRequest) {
        log.info("Start creating Book. BookRequest: {}", bookRequest);

        final Book book = transformer.transform(bookRequest);

        return repository.save(book);
    }

    public Book update(final Integer id, final BookRequest bookRequest) {
        log.info("Start updating Book. BookID: {}, BookRequest: {}", id, bookRequest);

        final Book book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource Not Found"));

        transformer.transform(bookRequest, book);

        return repository.save(book);
    }

    public Book findOneOrThrowException(final Integer id) {
        log.debug("Start getting Book. BookID: {}", id);

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource Not Found"));
    }

    public List<Book> getBooks() {
        log.info("Get all books");

        return repository.findAll();
    }

}
