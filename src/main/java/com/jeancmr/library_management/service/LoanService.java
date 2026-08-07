package com.jeancmr.library_management.service;


import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.domain.Loan;
import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.dto.LoanRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;
import com.jeancmr.library_management.enums.LoanStatus;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.LoanMapper;
import com.jeancmr.library_management.repository.*;
import com.jeancmr.library_management.service.interfaces.IBookCopyService;
import com.jeancmr.library_management.service.interfaces.ILoanService;
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
    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    private final BookCopyRepository  bookCopyRepository;
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

        MemberProfile member = memberRepository.findById(loanRequestDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member",  "ID", loanRequestDto.getMemberId()));
        LibrarianProfile librarian = librarianRepository.findById(loanRequestDto.getLibrarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Librarian", "ID", loanRequestDto.getLibrarianId()));
        BookCopy bookCopy = bookCopyRepository.findById(loanRequestDto.getBookCopyId())
                .orElseThrow(() -> new ResourceNotFoundException("Book Copy", "ID", loanRequestDto.getBookCopyId()));

        bookCopy.setStatus(BookCopyStatus.LOANED);

        BookCopy bookCopyUpdated =  bookCopyRepository.save(bookCopy);

        newLoan.setMember(member);
        newLoan.setLibrarian(librarian);
        newLoan.setBookCopy(bookCopyUpdated);

        newLoan.setLoanDate(LocalDate.now());
        newLoan.setStatus(LoanStatus.ACTIVE);

        return loanRepository.save(newLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findByMemberId(Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }
}
