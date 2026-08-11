package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibrarianService {
    List<LibrarianProfile> findAll();
    LibrarianProfile findById(Long id);
    LibrarianProfile save(UserCreateRequestDto userCreateRequestDto);
    LibrarianProfile update(Long id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteById(Long id);
}
