package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopyResponseDto;
import com.jeancmr.library_management.dto.BookCopyStatusRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;
import com.jeancmr.library_management.service.interfaces.IBookCopyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book-copies")
public class BookCopyController {

    private final IBookCopyService bookCopyService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<List<BookCopyResponseDto>> findAll() {
        return ResponseEntity.ok(bookCopyService.findAll());
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<List<BookCopyResponseDto>> findByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookCopyService.findByBookId(bookId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookCopyResponseDto> create(@Valid @RequestBody BookCopyRequestDto requestDto) {
        BookCopyResponseDto responseDto = bookCopyService.create(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookCopyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookCopyResponseDto> updateStatus(@PathVariable Long id,
                                                            @RequestBody BookCopyStatusRequestDto updateStatusDto)
    {
        BookCopyResponseDto responseDto = bookCopyService.updateStatus(id, updateStatusDto);
        return ResponseEntity.ok(responseDto);
    }
}
