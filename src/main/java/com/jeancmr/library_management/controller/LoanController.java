package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Loan.LoanRequestDto;
import com.jeancmr.library_management.dto.Loan.LoanResponseDto;
import com.jeancmr.library_management.dto.Loan.LoanResponseSummaryDto;
import com.jeancmr.library_management.mapper.LoanMapper;
import com.jeancmr.library_management.service.interfaces.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Operations related to loans")
public class LoanController {

    private final ILoanService loanService;
    private final LoanMapper loanMapper;

    @GetMapping
    @Operation(summary = "Getting all loans")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loans found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<LoanResponseSummaryDto>> getAllLoans() {
        List<LoanResponseSummaryDto> loans = loanService.findAll()
                .stream().map(loanMapper::toSummaryDto).toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("{id}")
    @Operation(summary = "Getting loan by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loan found"),
                    @ApiResponse(responseCode = "404", description = "Loan not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id){
        LoanResponseDto foundLoan = loanMapper.toDto(loanService.findById(id));
        return ResponseEntity.ok(foundLoan);
    }

    @GetMapping("/book/{memberId}")
    @Operation(summary = "Getting loans by member id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loans found"),
                    @ApiResponse(responseCode = "404", description = "Member not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<LoanResponseSummaryDto>> getLoansByMemberId(@PathVariable Long memberId){
        List<LoanResponseSummaryDto> loansByMember = loanService.findByMemberId(memberId)
                .stream().map(loanMapper::toSummaryDto).toList();
        return ResponseEntity.ok(loansByMember);
    }

    @PostMapping
    @Operation(summary = "Save loan")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loan saved successfully"),
                    @ApiResponse(responseCode = "404", description = "Book copy, member or librarian not found"),
                    @ApiResponse(responseCode = "409", description = "Book copy not available"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<LoanResponseDto> saveLoan(@Valid @RequestBody LoanRequestDto loanRequestDto) {
        LoanResponseDto savedLoan = loanMapper.toDto(loanService.save(loanRequestDto));
        return ResponseEntity.ok(savedLoan);
    }
}
