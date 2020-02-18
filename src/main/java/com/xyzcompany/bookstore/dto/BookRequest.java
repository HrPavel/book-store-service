package com.xyzcompany.bookstore.dto;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class BookRequest {

    @NotBlank
    private String isbn;

    @NotBlank
    private String name;

    @NotBlank
    private String author;

    private List<String> categories;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(final List<String> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object that) {
        return reflectionEquals(this, that);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
