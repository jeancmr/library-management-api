package com.jeancmr.library_management.dto;

import com.jeancmr.library_management.enums.MemberStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private LocalDate birthDate;
    private MemberStatus status;
    private LocalDate membershipDate;
    private int borrowLimit;
}
