package com.jeancmr.library_management.dto.BookCopy;

import com.jeancmr.library_management.enums.BookCopyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyStatusRequestDto {

    @NotNull(message = "status should not be null")
    @NotBlank(message = "status should not be empty")
    private BookCopyStatus status;
}
