package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.dto.Book.BookRequestDto;
import com.jeancmr.library_management.dto.Book.BookResponseDto;
import com.jeancmr.library_management.mapper.BookMapper;
import com.jeancmr.library_management.service.interfaces.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Books", description = "Operations related to books")
public class BookController {

    private final IBookService bookService;
    private final BookMapper       bookMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    @Operation(summary = "Getting all books")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Books found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public List<BookResponseDto> getBooks() {
        return bookService.findAll().stream()
                .map(bookMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting book by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book found"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        Book bookFound = bookService.findById(id);
        BookResponseDto responseDto = bookMapper.toResponseDto(bookFound);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Save book")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Book saved successfully"),
                    @ApiResponse(responseCode = "404", description = "Publisher, author or category not found"),
                    @ApiResponse(responseCode = "409", description = "Book with the same ISBN already registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        Book bookSaved = bookService.save(bookRequestDto);
        BookResponseDto responseDto = bookMapper.toResponseDto(bookSaved);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
