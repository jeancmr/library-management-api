package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.LibrarianCreateRequestDto;
import com.jeancmr.library_management.dto.LibrarianResponseDto;
import com.jeancmr.library_management.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.service.ILibrarianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/librarians")
@RequiredArgsConstructor
public class LibrarianController {

    private final ILibrarianService  librarianService;

    @GetMapping
    public List<LibrarianResponseDto> findAll() {
        return librarianService.findAll();
    }

    @PostMapping
    public ResponseEntity<LibrarianResponseDto> save(@Valid @RequestBody LibrarianCreateRequestDto requestDto) {
        return new ResponseEntity<>(librarianService.save(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibrarianResponseDto> update(@PathVariable Long id,
                                                    @RequestBody UserUpdateRequestDto requestDto) {
        return ResponseEntity.ok(librarianService.update(id, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibrarianResponseDto> findById(@PathVariable Long id) {
        LibrarianResponseDto foundMember = librarianService.findById(id);
        return ResponseEntity.ok(foundMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        librarianService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
