package com.xyzcompany.bookstore.rest;

import static org.springframework.http.ResponseEntity.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xyzcompany.bookstore.domain.Book;
import com.xyzcompany.bookstore.dto.BookRequest;
import com.xyzcompany.bookstore.service.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookResource {

    private BookService bookService;

    public BookResource(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody final BookRequest bookRequest) throws Exception {
        final Book book = bookService.create(bookRequest);

        return created(new URI("/api/books/" + book.getId())).body(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable final Integer id,
            @Valid @RequestBody final BookRequest bookRequest) {
        return ok(bookService.update(id, bookRequest));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return ok(bookService.getBooks());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable final Integer id) {
        return ok(bookService.findOneOrThrowException(id));
    }
}
