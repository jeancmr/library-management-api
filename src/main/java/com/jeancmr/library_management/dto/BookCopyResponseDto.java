package com.jeancmr.library_management.dto;

import com.jeancmr.library_management.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyResponseDto {
    private Long id;
    private BookCopyStatus status;
    private Long bookId;
}
