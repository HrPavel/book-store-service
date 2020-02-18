package com.xyzcompany.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }
}