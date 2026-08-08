package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Author.AuthorDto;
import com.jeancmr.library_management.service.interfaces.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final IAuthorService authorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    public ResponseEntity<List<AuthorDto>> findAllAuthors(){
        List<AuthorDto> authorList = authorService.findAll();
        return ResponseEntity.ok(authorList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable Long id){
        AuthorDto authorDtoFound = authorService.findById(id);
        return ResponseEntity.ok(authorDtoFound);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDto){
        AuthorDto authorDtoFound = authorService.save(authorDto);
        return new ResponseEntity<>(authorDtoFound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto){
        AuthorDto authorUpdated = authorService.update(id, authorDto);
        return ResponseEntity.ok(authorUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}