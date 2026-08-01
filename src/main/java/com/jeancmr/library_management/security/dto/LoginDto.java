package com.jeancmr.library_management.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message="Email cannot be empty")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message="Password cannot be empty")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;
}
