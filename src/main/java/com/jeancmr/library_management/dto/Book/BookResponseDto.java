package com.jeancmr.library_management.dto.Book;

import com.jeancmr.library_management.dto.Author.AuthorDto;
import com.jeancmr.library_management.dto.Category.CategoryDto;
import com.jeancmr.library_management.dto.Publisher.PublisherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private PublisherDto publisher;
    private Set<AuthorDto> authors;
    private Set<CategoryDto> categories;
}
