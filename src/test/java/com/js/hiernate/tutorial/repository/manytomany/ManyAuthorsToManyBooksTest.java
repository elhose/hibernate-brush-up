package com.js.hiernate.tutorial.repository.manytomany;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.manytomany.Author;
import com.js.hiernate.tutorial.entity.manytomany.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ManyAuthorsToManyBooksTest extends PostgresInitializer {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void cleanUp() {
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Saving only Books works")
    void booksSave() {
        var firstBook = Book.builder()
                            .title("DDD")
                            .price(BigDecimal.valueOf(12.34))
                            .build();

        var secondBook = Book.builder()
                             .title("TDD")
                             .price(BigDecimal.valueOf(4.32))
                             .isOnPromotion(true)
                             .build();

        final var savedBooks = bookRepository.saveAll(List.of(firstBook, secondBook));

        assertThat(savedBooks)
                .hasSize(2)
                .contains(firstBook, secondBook)
                .extracting(Book::getId)
                .isNotEqualTo(0);
    }

    @Test
    @DisplayName("Saving only Authors works")
    void authorSave() {
        var firstAuthor = Author.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .build();

        var secondAuthor = Author.builder()
                                 .firstName("Michael")
                                 .lastName("Scott")
                                 .build();

        final var savedAuthors = authorRepository.saveAll(List.of(firstAuthor, secondAuthor));

        assertThat(savedAuthors)
                .hasSize(2)
                .contains(firstAuthor, secondAuthor)
                .extracting(Author::getId)
                .isNotEqualTo(0);
    }

    @Test
    @DisplayName("Saving Books first, Authors second works")
    void booksAuthorsSave() {
        var firstBook = Book.builder()
                            .title("DDD")
                            .price(BigDecimal.valueOf(12.34))
                            .build();
        var secondBook = Book.builder()
                             .title("TDD")
                             .price(BigDecimal.valueOf(4.32))
                             .isOnPromotion(true)
                             .build();

        var firstAuthor = Author.builder()
                                .firstName("Kevin")
                                .lastName("Malone")
                                .books(Set.of(firstBook))
                                .build();

        var secondAuthor = Author.builder()
                                 .firstName("Jim")
                                 .lastName("Halpert")
                                 .books(Set.of(firstBook, secondBook))
                                 .build();
        var thirdAuthor = Author.builder()
                                .firstName("Creed")
                                .lastName("Bratton")
                                .build();

        bookRepository.saveAll(List.of(firstBook, secondBook));

        var savedAuthors = authorRepository.saveAll(List.of(firstAuthor, secondAuthor, thirdAuthor));

        assertThat(savedAuthors)
                .hasSize(3)
                .contains(firstAuthor, secondAuthor, thirdAuthor)
                .satisfies(book -> assertThat(book).extracting(Author::getBooks).isNotNull())
                .extracting(Author::getId)
                .isNotEqualTo(0);
    }

    @Test
    @DisplayName("Saving Authors first, Books second works")
    void authorsBooksSave() {
        var firstAuthor = Author.builder()
                                .firstName("Kevin")
                                .lastName("Malone")
                                .build();
        var secondAuthor = Author.builder()
                                 .firstName("Jim")
                                 .lastName("Halpert")
                                 .build();
        var thirdAuthor = Author.builder()
                                .firstName("Creed")
                                .lastName("Bratton")
                                .build();

        var firstBook = Book.builder()
                            .title("DDD")
                            .price(BigDecimal.valueOf(12.34))
                            .authors(Set.of(firstAuthor, secondAuthor))
                            .build();
        var secondBook = Book.builder()
                             .title("TDD")
                             .price(BigDecimal.valueOf(4.32))
                             .isOnPromotion(true)
                             .authors(Set.of(secondAuthor))
                             .build();

        authorRepository.saveAll(List.of(firstAuthor, secondAuthor, thirdAuthor));

        final var savedBooks = bookRepository.saveAll(List.of(firstBook, secondBook));

        assertThat(savedBooks)
                .hasSize(2)
                .contains(firstBook, secondBook)
                .satisfies(book -> assertThat(book).extracting(Book::getAuthors).isNotNull())
                .extracting(Book::getId)
                .isNotEqualTo(0);
    }
}