package com.jeancmr.library_management.dto.Book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {
    private Long id;

    @NotBlank(message = "title should not be empty")
    private String title;

    @NotBlank(message = "isbn should not be empty")
    private String isbn;

    @Past(message = "publicationDate must be in the past")
    @NotNull(message = "Birthdate cannot be null")
    private LocalDate publicationDate;

    @NotNull(message = "publisherId cannot be null")
    private Long publisherId;

    @NotNull(message = "authorsId cannot be null")
    private Set<Long> authorsId;

    @NotNull(message = "categoriesId cannot be null")
    private Set<Long> categoriesId;
}
