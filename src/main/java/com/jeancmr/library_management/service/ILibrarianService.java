package com.jeancmr.library_management.service;

import com.jeancmr.library_management.dto.*;
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
