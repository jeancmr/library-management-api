package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.domain.User;
import com.jeancmr.library_management.dto.LibrarianResponseDto;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.enums.Role;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.LibrarianProfileMapper;
import com.jeancmr.library_management.mapper.UserMapper;
import com.jeancmr.library_management.repository.LibrarianRepository;
import com.jeancmr.library_management.repository.UserRepository;
import com.jeancmr.library_management.service.interfaces.ILibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibrarianService implements ILibrarianService {

    private final LibrarianRepository  librarianRepository;
    private final UserRepository userRepository;
    private final LibrarianProfileMapper librarianMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<LibrarianResponseDto> findAll() {
        return librarianRepository.findAll().stream()
                .map(librarianMapper::toResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LibrarianResponseDto findById(Long id) {
        LibrarianProfile foundLibrarian = librarianRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Librarian", "ID", id));
        return librarianMapper.toResponseDto(foundLibrarian);
    }

    @Override
    @Transactional
    public LibrarianResponseDto save(UserCreateRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", requestDto.getEmail());
        }

        User user = userMapper.toEntity(requestDto);

        LibrarianProfile librarianProfile = librarianMapper.toEntity(requestDto);
        librarianProfile.setHiredDate(LocalDate.now());

        user.setRoles(Set.of(Role.ROLE_LIBRARIAN));
        user.assignLibrarianProfile(librarianProfile);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User savedUser = userRepository.save(user);

        return librarianMapper.toResponseDto(savedUser.getLibrarianProfile());
    }

    @Override
    @Transactional
    public LibrarianResponseDto update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User existingUser = librarianRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Librarian", "ID", id))
                        .getUser();

        userMapper.updateUser(userUpdateRequestDto, existingUser);
        librarianMapper.updateLibrarianFromDto(userUpdateRequestDto, existingUser.getLibrarianProfile());

        User updatedUser = userRepository.save(existingUser);

        return librarianMapper.toResponseDto(updatedUser.getLibrarianProfile());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(!librarianRepository.existsById(id)) {
            throw new ResourceNotFoundException("Librarian", "ID", id);
        }
        userRepository.deleteById(id);
    }
}
