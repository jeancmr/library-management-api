package com.jeancmr.library_management.dto.Loan;


import com.jeancmr.library_management.dto.BookCopy.BookCopyResponseSummaryDto;
import com.jeancmr.library_management.dto.Librarian.LibrarianResponseDto;
import com.jeancmr.library_management.dto.Librarian.LibrarianResponseSummaryDto;
import com.jeancmr.library_management.dto.Member.MemberResponseSummaryDto;
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
public class LoanResponseSummaryDto {
    private Long id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private LibrarianResponseSummaryDto librarian;
    private MemberResponseSummaryDto member;
    private BookCopyResponseSummaryDto bookCopy;
}
