package com.jeancmr.library_management.repository;

import com.jeancmr.library_management.domain.Book;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {
            "publisher",
            "authors",
            "categories"
    })
    List<Book> findAll();
}
