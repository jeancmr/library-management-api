package com.jeancmr.library_management.dto;

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
    private LocalDate membershipDate;
    private int borrowLimit;
}
