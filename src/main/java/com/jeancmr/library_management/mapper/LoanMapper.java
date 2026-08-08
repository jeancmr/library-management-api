package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.Loan;
import com.jeancmr.library_management.dto.Loan.LoanRequestDto;
import com.jeancmr.library_management.dto.Loan.LoanResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {
                MemberProfileMapper.class,
                LibrarianProfileMapper.class,
        })

public interface LoanMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "librarian", ignore = true)
    @Mapping(target = "bookCopy", ignore = true)
    Loan toEntity(LoanRequestDto loanRequestDto);

    @Mapping(target = "bookCopy", source = "bookCopy")
    LoanResponseDto toDto(Loan loan);
}
