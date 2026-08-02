package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.dto.AuthorDto;

import java.util.List;

public interface IAuthorService {
    List<AuthorDto> findAll();
    AuthorDto findById(Long id);
    AuthorDto save(AuthorDto authorDto);
    AuthorDto update(Long id, AuthorDto authorDto);
    void deleteById(Long id);
}