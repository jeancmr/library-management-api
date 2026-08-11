package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Loan.LoanRequestDto;
import com.jeancmr.library_management.dto.Loan.LoanResponseDto;
import com.jeancmr.library_management.dto.Loan.LoanResponseSummaryDto;
import com.jeancmr.library_management.mapper.LoanMapper;
import com.jeancmr.library_management.service.interfaces.ILoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final ILoanService loanService;
    private final LoanMapper loanMapper;

    @GetMapping
    public ResponseEntity<List<LoanResponseSummaryDto>> getAllLoans() {
        List<LoanResponseSummaryDto> loans = loanService.findAll()
                .stream().map(loanMapper::toSummaryDto).toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id){
        LoanResponseDto foundLoan = loanMapper.toDto(loanService.findById(id));
        return ResponseEntity.ok(foundLoan);
    }

    @GetMapping("/book/{memberId}")
    public ResponseEntity<List<LoanResponseSummaryDto>> getLoansByMemberId(@PathVariable Long memberId){
        List<LoanResponseSummaryDto> loansByMember = loanService.findByMemberId(memberId)
                .stream().map(loanMapper::toSummaryDto).toList();
        return ResponseEntity.ok(loansByMember);
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> saveLoan(@Valid @RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto savedLoan = loanMapper.toDto(loanService.save(loanRequestDto));
        return ResponseEntity.ok(savedLoan);
    }
}
