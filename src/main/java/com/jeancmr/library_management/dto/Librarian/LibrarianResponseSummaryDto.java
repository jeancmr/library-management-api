package com.jeancmr.library_management.dto.Librarian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LibrarianResponseSummaryDto {
    private Long id;
    private String firstName;
    private String firstSurname;

}
