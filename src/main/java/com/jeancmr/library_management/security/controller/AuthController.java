package com.jeancmr.library_management.security.controller;

import com.jeancmr.library_management.domain.User;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.mapper.UserMapper;
import com.jeancmr.library_management.repository.UserRepository;
import com.jeancmr.library_management.security.dto.JwtAuthResponseDto;
import com.jeancmr.library_management.security.dto.LoginDto;
import com.jeancmr.library_management.security.jwt.JwtGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateJwtToken(authentication);

        return new ResponseEntity<>(new JwtAuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserCreateRequestDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new ResourceAlreadyExistsException("User",  "email", registerDto.getEmail());
        }

        User user = userMapper.toEntity(registerDto);
        user.setRoles(registerDto.getRoles());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);

        return  new ResponseEntity<>("User registered successfully.",
                HttpStatus.CREATED);
    }
}
