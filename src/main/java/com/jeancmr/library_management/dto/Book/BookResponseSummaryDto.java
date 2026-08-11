package com.jeancmr.library_management.dto.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookResponseSummaryDto {
    private Long id;
    private String title;
}
