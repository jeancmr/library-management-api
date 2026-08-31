package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.dto.Librarian.LibrarianResponseDto;
import com.jeancmr.library_management.mapper.LibrarianProfileMapper;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.service.interfaces.ILibrarianService;
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
@RequestMapping("api/v1/librarians")
@RequiredArgsConstructor
@Tag(name = "Librarians", description = "Operations related to librarians")
public class LibrarianController {

    private final ILibrarianService  librarianService;
    private final LibrarianProfileMapper librarianProfileMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting all librarians")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Librarians found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public List<LibrarianResponseDto> findAll() {
        return librarianService.findAll()
                .stream().map(librarianProfileMapper::toResponseDto).toList();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Save librarian")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Librarian saved successfully"),
                    @ApiResponse(responseCode = "409", description = "User with the same email already registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<LibrarianResponseDto> save(@Valid @RequestBody UserCreateRequestDto requestDto) {
        LibrarianProfile savedLibrarian =  librarianService.save(requestDto);
        LibrarianResponseDto responseDto = librarianProfileMapper.toResponseDto(savedLibrarian);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Update librarian")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Librarian updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Librarian not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<LibrarianResponseDto> update(@PathVariable Long id,
                                                    @RequestBody UserUpdateRequestDto requestDto) {
        LibrarianProfile updatedLibrarian = librarianService.update(id, requestDto);
        LibrarianResponseDto responseDto = librarianProfileMapper.toResponseDto(updatedLibrarian);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting librarian by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Librarian found"),
                    @ApiResponse(responseCode = "404", description = "Librarian not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<LibrarianResponseDto> findById(@PathVariable Long id) {
        LibrarianProfile foundMember = librarianService.findById(id);
        LibrarianResponseDto  responseDto = librarianProfileMapper.toResponseDto(foundMember);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Delete librarian")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "Librarian not found"),
                    @ApiResponse(responseCode = "204", description = "Librarian deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        librarianService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
