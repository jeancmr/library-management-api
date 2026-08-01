package com.jeancmr.library_management.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter @Setter
@Builder
public class ErrorResponse {
    private String error;
    private String message;
    private LocalDate timestamp;
    private int status;
}
