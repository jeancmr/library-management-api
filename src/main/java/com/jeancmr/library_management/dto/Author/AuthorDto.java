package com.jeancmr.library_management.dto.Author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AuthorDto {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String nationality;
    private String biography;
}