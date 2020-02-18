package com.xyzcompany.bookstore.domain;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "books_categories", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    @Column(name = "book_category")
    private List<String> categories;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
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
