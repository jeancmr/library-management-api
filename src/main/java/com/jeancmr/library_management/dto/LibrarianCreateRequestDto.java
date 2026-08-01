package com.jeancmr.library_management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibrarianCreateRequestDto {

    @NotBlank(message = "firstName cannot be empty")
    @Size(min = 3, max = 50,
            message = "firstName must be between 3 and 50 characters.")
    private String firstName;

    private String secondName;

    @NotBlank(message = "firstSurname cannot be empty")
    @Size(min = 3, max = 50,
            message = "firstSurname must be between 3 and 50 characters.")
    private String firstSurname;

    private String secondSurname;

    @NotBlank(message="Email cannot be empty")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message="Password cannot be empty")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;

    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate cannot be in the future.")
    private LocalDate birthDate;
}
