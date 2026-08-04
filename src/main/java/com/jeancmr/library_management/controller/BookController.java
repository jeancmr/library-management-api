package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.dto.BookRequestDto;
import com.jeancmr.library_management.dto.BookResponseDto;
import com.jeancmr.library_management.mapper.BookMapper;
import com.jeancmr.library_management.service.interfaces.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final IBookService bookService;
    private final BookMapper       bookMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    public List<BookResponseDto> getBooks() {
        return bookService.findAll().stream()
                .map(bookMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        Book bookFound = bookService.findById(id);
        BookResponseDto responseDto = bookMapper.toResponseDto(bookFound);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        Book bookSaved = bookService.save(bookRequestDto);
        BookResponseDto responseDto = bookMapper.toResponseDto(bookSaved);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
