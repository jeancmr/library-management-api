package com.jeancmr.library_management.dto.BookCopy;

import com.jeancmr.library_management.enums.BookCopyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyRequestDto {
    @NotNull(message = "bookId cannot be null")
    private Long bookId;

    private BookCopyStatus status;
}
