package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.dto.BookCopy.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopy.BookCopyResponseDto;
import com.jeancmr.library_management.dto.BookCopy.BookCopyResponseSummaryDto;
import com.jeancmr.library_management.dto.BookCopy.BookCopyStatusRequestDto;
import com.jeancmr.library_management.mapper.BookMapper;
import com.jeancmr.library_management.service.interfaces.IBookCopyService;
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
@RequestMapping("/api/v1/book-copies")
@Tag(name = "Book Copies", description = "Operations related to book copies")
public class BookCopyController {

    private final IBookCopyService bookCopyService;
    private final BookMapper bookMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting all book copies")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book copies found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<BookCopyResponseSummaryDto>> findAll() {
        List<BookCopyResponseSummaryDto> bookCopyList = bookCopyService.findAll()
                .stream()
                .map(this::toResponseSummaryDto).toList();
        return ResponseEntity.ok(bookCopyList);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting book copies by book id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book copies found"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<BookCopyResponseSummaryDto>> findByBookId(@PathVariable Long bookId) {
        List<BookCopyResponseSummaryDto> bookCopyList = bookCopyService.findByBookId(bookId)
                .stream().map(this::toResponseSummaryDto).toList();
        return ResponseEntity.ok(bookCopyList);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Save book copy")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Book copy saved successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<BookCopyResponseDto> create(@Valid @RequestBody BookCopyRequestDto requestDto) {
        BookCopyResponseDto responseDto = toResponseDto(bookCopyService.create(requestDto));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Delete book copy")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "Book copy not found"),
                    @ApiResponse(responseCode = "204", description = "Book copy deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookCopyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Update book copy status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book copy status updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Book copy not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
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

    private BookCopyResponseSummaryDto toResponseSummaryDto(BookCopy bookCopy) {
        BookCopyResponseSummaryDto responseDto = new BookCopyResponseSummaryDto();
        responseDto.setId(bookCopy.getId());
        responseDto.setStatus(bookCopy.getStatus());
        responseDto.setOriginBook(bookMapper.toResponseSummaryDto(bookCopy.getBook()));

        return responseDto;
    }
}
