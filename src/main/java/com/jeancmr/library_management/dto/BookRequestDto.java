package com.jeancmr.library_management.dto;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.domain.Publisher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
public class BookRequestDto {
    private Long id;

    private String title;

    private String isbn;

    private LocalDate publicationDate;

    private Long publisherId;

    private Set<Long> authorsId;

    private Set<Long> categoriesId;
}
