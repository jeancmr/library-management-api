package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.Loan;
import com.jeancmr.library_management.dto.Loan.LoanRequestDto;

import java.util.List;

public interface ILoanService {
    List<Loan> findAll();
    Loan findById(Long loanId);
    Loan save (LoanRequestDto loanRequestDto);
    List<Loan> findByMemberId(Long memberId);
}
