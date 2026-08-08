package com.jeancmr.library_management.service;


import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.domain.Loan;
import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.dto.Loan.LoanRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;
import com.jeancmr.library_management.enums.LoanStatus;
import com.jeancmr.library_management.exception.BookCopyNotAvailableException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.LoanMapper;
import com.jeancmr.library_management.repository.*;
import com.jeancmr.library_management.service.interfaces.IBookCopyService;
import com.jeancmr.library_management.service.interfaces.ILibrarianService;
import com.jeancmr.library_management.service.interfaces.ILoanService;
import com.jeancmr.library_management.service.interfaces.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService implements ILoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper  loanMapper;
    private final IMemberService memberService;
    private final ILibrarianService librarianService;
    private final IBookCopyService  bookCopyService;

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Loan findById(Long loanId) {
        return loanRepository.findById(loanId).
                orElseThrow(() -> new ResourceNotFoundException("Loan", "id", loanId));
    }

    @Override
    @Transactional
    public Loan save(LoanRequestDto loanRequestDto) {
        Loan  newLoan = loanMapper.toEntity(loanRequestDto);

        MemberProfile member = memberService.findById(loanRequestDto.getMemberId());
        LibrarianProfile librarian = librarianService.findById(loanRequestDto.getLibrarianId());
        BookCopy bookCopy = bookCopyService.findById(loanRequestDto.getBookCopyId());

        if (bookCopy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new BookCopyNotAvailableException(bookCopy.getId());
        }

        bookCopy.setStatus(BookCopyStatus.LOANED);

        newLoan.setMember(member);
        newLoan.setLibrarian(librarian);
        newLoan.setBookCopy(bookCopy);

        newLoan.setLoanDate(LocalDate.now());
        newLoan.setStatus(LoanStatus.ACTIVE);

        return loanRepository.save(newLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findByMemberId(Long memberId) {
        if(memberService.findById(memberId) == null) {
            throw new ResourceNotFoundException("Member", "id", memberId);
        }
        return loanRepository.findByMemberId(memberId);
    }
}
