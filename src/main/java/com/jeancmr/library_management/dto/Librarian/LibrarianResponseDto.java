package com.jeancmr.library_management.dto.Librarian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class LibrarianResponseDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private LocalDate birthDate;
    private LocalDate hiredDate;

}
