package com.jeancmr.library_management.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseSummaryDto {
    private Long id;
    private String firstName;
    private String firstSurname;

}
