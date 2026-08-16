package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.*;
import com.jeancmr.library_management.dto.Loan.LoanRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;
import com.jeancmr.library_management.enums.LoanStatus;
import com.jeancmr.library_management.exception.BookCopyNotAvailableException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.LoanMapper;
import com.jeancmr.library_management.repository.LoanRepository;
import com.jeancmr.library_management.service.interfaces.IBookCopyService;
import com.jeancmr.library_management.service.interfaces.ILibrarianService;
import com.jeancmr.library_management.service.interfaces.IMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private IMemberService memberService;

    @Mock
    private ILibrarianService librarianService;

    @Mock
    private IBookCopyService bookCopyService;

    @InjectMocks
    private LoanService loanService;

    @Test
    @DisplayName("Should return all loans successfully")
    void shouldReturnAllLoansSuccessfully() {
        List<Loan> loanList = List.of(new Loan(), new Loan(), new Loan());

        when(loanRepository.findAll()).thenReturn(loanList);

        List<Loan> result =  loanService.findAll();

        assertSame(loanList, result);
        assertEquals(loanList.size(), result.size());

        verify(loanRepository).findAll();
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("Should return empty list when there are no loans")
    void  shouldReturnEmptyListWhenThereAreNoLoans() {
        when(loanRepository.findAll()).thenReturn(Collections.emptyList());

        List<Loan> result  =  loanService.findAll();

        assertTrue(result.isEmpty());

        verify(loanRepository).findAll();
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("Should return Loan By Id when Loan exists")
    void shouldReturnLoanByIdWhenLoanExists() {
        Long  id = 10L;
        Loan loan = new Loan();
        loan.setId(id);

        when(loanRepository.findById(id)).thenReturn(Optional.of(loan));

        Loan result = loanService.findById(id);

        assertSame(loan, result);
        verify(loanRepository).findById(id);
        verifyNoMoreInteractions(loanRepository);
    }

    @Test
    @DisplayName("Should throw Exception when Loan does not exists")
    void  shouldThrowExceptionWhenLoanDoesNotExist() {
        Long  id = 10L;
        when(loanRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException exception =  assertThrows(ResourceNotFoundException.class, () -> {
            loanService.findById(id);
        });

        assertEquals("Loan with id '" + id + "' not found", exception.getMessage());

        verify(loanRepository).findById(id);
        verifyNoMoreInteractions(loanRepository);

    }

    @Test
    @DisplayName("Should return all Loans by Member using memberId")
    void shouldReturnAllLoansByMemberId() {
        Long memberId = 20L;
        MemberProfile member = new MemberProfile();
        member.setId(memberId);

        Loan loan1 = new Loan();
        loan1.setMember(member);

        Loan loan2 = new Loan();
        loan2.setMember(member);

        List<Loan> loanList = List.of(loan1, loan2);

        when(memberService.findById(memberId)).thenReturn(member);
        when(loanRepository.findByMemberId(memberId)).thenReturn(loanList);

        List<Loan> result = loanService.findByMemberId(memberId);

        assertSame(loanList, result);

        verify(memberService).findById(memberId);
        verify(loanRepository).findByMemberId(memberId);
        verifyNoMoreInteractions(loanRepository,memberService);
    }

    @Test
    @DisplayName("Should throw exception when Member does not exist")
    void shouldThrowExceptionWhenMemberDoesNotExist() {
        Long memberId = 20L;

        when(memberService.findById(memberId)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> loanService.findByMemberId(memberId)
        );

        assertEquals(
                "Member with id '" + memberId + "' not found",
                exception.getMessage()
        );

        verify(memberService).findById(memberId);
        verifyNoInteractions(loanRepository);
    }

    @Test
    @DisplayName("Should save successfully Loan when bookCopy is available")
    void shouldSaveLoanSuccessfullyWhenBookCopyIsAvailable() {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(30L);
        bookCopy.setStatus(BookCopyStatus.AVAILABLE);

        LibrarianProfile librarian = new LibrarianProfile();
        librarian.setId(10L);

        MemberProfile member = new MemberProfile();
        member.setId(11L);

        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setBookCopyId(bookCopy.getId());
        loanRequestDto.setLibrarianId(librarian.getId());
        loanRequestDto.setMemberId(member.getId());
        loanRequestDto.setDueDate(LocalDate.of(2027,5,1));

        Loan loanToSave =  new Loan();
        loanToSave.setDueDate(LocalDate.of(2027,5,1));

        Loan savedLoan = new Loan();
        savedLoan.setId(10L);
        savedLoan.setBookCopy(bookCopy);
        savedLoan.setLibrarian(librarian);
        savedLoan.setMember(member);
        savedLoan.setLoanDate(LocalDate.now());
        savedLoan.setDueDate(LocalDate.of(2027,5,1));

        when(loanMapper.toEntity(loanRequestDto)).thenReturn(loanToSave);

        when(memberService.findById(loanRequestDto.getMemberId())).thenReturn(member);
        when(librarianService.findById(loanRequestDto.getLibrarianId())).thenReturn(librarian);
        when(bookCopyService.findById(loanRequestDto.getBookCopyId())).thenReturn(bookCopy);

        when(loanRepository.save(loanToSave)).thenReturn(savedLoan);

        Loan result =  loanService.save(loanRequestDto);

        assertSame(savedLoan, result);

        assertSame(bookCopy, loanToSave.getBookCopy());
        assertSame(librarian, loanToSave.getLibrarian());
        assertSame(member, loanToSave.getMember());
        assertEquals(BookCopyStatus.LOANED, bookCopy.getStatus());
        assertEquals(LoanStatus.ACTIVE, loanToSave.getStatus());
        assertEquals(LocalDate.now(), loanToSave.getLoanDate());

        verify(loanMapper).toEntity(loanRequestDto);
        verify(memberService).findById(loanRequestDto.getMemberId());
        verify(librarianService).findById(loanRequestDto.getLibrarianId());
        verify(bookCopyService).findById(loanRequestDto.getBookCopyId());
        verify(loanRepository).save(loanToSave);
        verifyNoMoreInteractions(
                loanMapper,
                memberService,
                librarianService,
                bookCopyService,
                loanRepository
        );
    }

    @Test
    @DisplayName("Should throw exception when book copy is not available")
    void shouldThrowExceptionWhenBookCopyIsNotAvailable() {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(30L);
        bookCopy.setStatus(BookCopyStatus.LOANED);

        LoanRequestDto request = new LoanRequestDto();
        request.setBookCopyId(bookCopy.getId());
        request.setMemberId(11L);
        request.setLibrarianId(10L);

        when(bookCopyService.findById(request.getBookCopyId()))
                .thenReturn(bookCopy);

        BookCopyNotAvailableException exception = assertThrows(
                BookCopyNotAvailableException.class,
                () -> loanService.save(request)
        );

        assertEquals("Book copy with id " + bookCopy.getId()
                + " is not available", exception.getMessage());

        verify(bookCopyService).findById(request.getBookCopyId());
        verifyNoInteractions(
                loanMapper,
                memberService,
                librarianService,
                loanRepository
        );
    }
}