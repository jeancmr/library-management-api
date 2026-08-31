package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Author.AuthorDto;
import com.jeancmr.library_management.service.interfaces.IAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Operations related to authors")

public class AuthorController {
    private final IAuthorService authorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    @Operation(summary = "Getting all authors")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Authors founds"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<AuthorDto>> findAllAuthors(){
        List<AuthorDto> authorList = authorService.findAll();
        return ResponseEntity.ok(authorList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting author by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Author found"),
                    @ApiResponse(responseCode = "404", description = "Author not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable Long id){
        AuthorDto authorDtoFound = authorService.findById(id);
        return ResponseEntity.ok(authorDtoFound);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Save author")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Author saved successfully"),
                    @ApiResponse(responseCode = "409", description = "Author already registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDto){
        AuthorDto authorDtoFound = authorService.save(authorDto);
        return new ResponseEntity<>(authorDtoFound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Update author")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Author updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Author not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto){
        AuthorDto authorUpdated = authorService.update(id, authorDto);
        return ResponseEntity.ok(authorUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Delete author")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "Author not found"),
                    @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}