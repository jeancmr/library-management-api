package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.dto.*;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibrarianService {
    List<LibrarianResponseDto> findAll();
    LibrarianResponseDto findById(Long id);
    LibrarianResponseDto save(UserCreateRequestDto userCreateRequestDto);
    LibrarianResponseDto update(Long id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteById(Long id);
}
