package com.jeancmr.library_management.dto.Loan;

import com.jeancmr.library_management.dto.BookCopy.BookCopyResponseDto;
import com.jeancmr.library_management.dto.Librarian.LibrarianResponseDto;
import com.jeancmr.library_management.dto.Member.MemberResponseDto;
import com.jeancmr.library_management.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDto {
    private Long id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private MemberResponseDto member;
    private LibrarianResponseDto librarian;
    private BookCopyResponseDto bookCopy;
}
