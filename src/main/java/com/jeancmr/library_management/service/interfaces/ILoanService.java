package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.Loan;
import com.jeancmr.library_management.dto.LoanRequestDto;

import java.util.List;
import java.util.Optional;

public interface ILoanService {
    List<Loan> findAll();
    Loan findById(Long loanId);
    Loan save (LoanRequestDto loanRequestDto);
    List<Loan> findByMemberId(Long memberId);
}
