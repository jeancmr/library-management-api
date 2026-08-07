package com.jeancmr.library_management.dto;

import com.jeancmr.library_management.enums.LoanStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDto {
    private LocalDate loanDate;

    @NotNull
    @Future(message = "should be a future date")
    private LocalDate dueDate;

    @NotNull(message = "memberId should not be null")
    private Long memberId;

    @NotNull(message = "librarianId should not be null")
    private Long librarianId;

    @NotNull(message = "bookCopyId should not be null")
    private Long bookCopyId;
}
