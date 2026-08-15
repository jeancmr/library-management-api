package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.dto.Author.AuthorDto;

import java.util.List;

public interface IAuthorService {
    List<AuthorDto> findAll();
    AuthorDto findById(Long id);
    Author findEntityById(Long id);
    AuthorDto save(AuthorDto authorDto);
    AuthorDto update(Long id, AuthorDto authorDto);
    void deleteById(Long id);
}