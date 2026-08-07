package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.dto.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopyResponseDto;
import com.jeancmr.library_management.dto.BookCopyStatusRequestDto;
import com.jeancmr.library_management.mapper.BookMapper;
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
    private final BookMapper bookMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<List<BookCopyResponseDto>> findAll() {
        List<BookCopyResponseDto> bookCopyList = bookCopyService.findAll()
                .stream()
                .map(this::toResponseDto).toList();
        return ResponseEntity.ok(bookCopyList);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<List<BookCopyResponseDto>> findByBookId(@PathVariable Long bookId) {
        List<BookCopyResponseDto> bookCopyList = bookCopyService.findByBookId(bookId)
                .stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(bookCopyList);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookCopyResponseDto> create(@Valid @RequestBody BookCopyRequestDto requestDto) {
        BookCopyResponseDto responseDto = toResponseDto(bookCopyService.create(requestDto));
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
        BookCopyResponseDto responseDto = toResponseDto(bookCopyService.updateStatus(id, updateStatusDto));
        return ResponseEntity.ok(responseDto);
    }

    private BookCopyResponseDto toResponseDto(BookCopy bookCopy) {
        BookCopyResponseDto responseDto = new BookCopyResponseDto();
        responseDto.setId(bookCopy.getId());
        responseDto.setStatus(bookCopy.getStatus());
        responseDto.setBook(bookMapper.toResponseDto(bookCopy.getBook()));

        return responseDto;
    }
}
