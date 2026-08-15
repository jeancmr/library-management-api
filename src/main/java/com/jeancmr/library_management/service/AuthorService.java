package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.dto.Author.AuthorDto;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.AuthorMapper;
import com.jeancmr.library_management.repository.AuthorRepository;
import com.jeancmr.library_management.service.interfaces.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto findById(Long id) {
        Author foundAuthor = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author", "ID", id));
        return authorMapper.toDto(foundAuthor);
    }

    @Override
    @Transactional
    public AuthorDto save(AuthorDto authorDto) {
        if(authorRepository.existsByName(authorDto.getName())) {
            throw new ResourceAlreadyExistsException("Author", "name", authorDto.getName());
        }

        Author author = authorMapper.toEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toDto(savedAuthor);
    }

    @Override
    @Transactional
    public AuthorDto update(Long id, AuthorDto authorDto) {
        Author existingAuthor = authorMapper.toEntity(findById(id));
        authorMapper.update(authorDto, existingAuthor);
        Author updatedAuthor = authorRepository.save(existingAuthor);

        return authorMapper.toDto(updatedAuthor);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Long authorToDeleteId = findById(id).getId();

        authorRepository.deleteById(authorToDeleteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Author findEntityById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author", "ID", id));
    }
}