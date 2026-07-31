package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.domain.User;
import com.jeancmr.library_management.dto.LibrarianCreateRequestDto;
import com.jeancmr.library_management.dto.LibrarianResponseDto;
import com.jeancmr.library_management.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.enums.Role;
import com.jeancmr.library_management.mapper.LibrarianProfileMapper;
import com.jeancmr.library_management.mapper.UserMapper;
import com.jeancmr.library_management.repository.LibrarianRepository;
import com.jeancmr.library_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibrarianService implements ILibrarianService{

    private final LibrarianRepository  librarianRepository;
    private final UserRepository userRepository;
    private final LibrarianProfileMapper librarianMapper;
    private final UserMapper userMapper;


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
                orElseThrow(()-> new RuntimeException("No librarian found with id " + id));

        return librarianMapper.toResponseDto(foundLibrarian);
    }

    @Override
    @Transactional
    public LibrarianResponseDto save(LibrarianCreateRequestDto librarianCreateRequestDto) {
        if(userRepository.existsByEmail(librarianCreateRequestDto.getEmail())) {
            throw  new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(librarianCreateRequestDto);

        LibrarianProfile librarianProfile = librarianMapper.toEntity(librarianCreateRequestDto);
        librarianProfile.setHiredDate(LocalDate.now());

        user.setRoles(Set.of(Role.ROLE_LIBRARIAN));
        user.assignLibrarianProfile(librarianProfile);

        User savedUser = userRepository.save(user);

        return librarianMapper.toResponseDto(savedUser.getLibrarianProfile());
    }

    @Override
    @Transactional
    public LibrarianResponseDto update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Member not found"));

        userMapper.updateUser(userUpdateRequestDto, existingUser);
        librarianMapper.updateLibrarianFromDto(userUpdateRequestDto, existingUser.getLibrarianProfile());

        User updatedUser = userRepository.save(existingUser);

        return librarianMapper.toResponseDto(updatedUser.getLibrarianProfile());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(!librarianRepository.existsById(id)) {
            throw new RuntimeException("Librarian not found");
        }
        userRepository.deleteById(id);
    }
}
