package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.dto.AuthorDto;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.AuthorMapper;
import com.jeancmr.library_management.repository.AuthorRepository;
import com.jeancmr.library_management.service.interfaces.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    public AuthorDto findById(Long id) {
        Author foundAuthor = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(Author.class, id));
        return authorMapper.toDto(foundAuthor);
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        if(authorRepository.existsByName(authorDto.getName())) {
            throw new ResourceAlreadyExistsException("Author", "name", authorDto.getName());
        }

        Author author = authorMapper.toEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toDto(savedAuthor);
    }

    @Override
    public AuthorDto update(Long id, AuthorDto authorDto) {
        Author existingAuthor = authorMapper.toEntity(findById(id));
        authorMapper.update(authorDto, existingAuthor);
        Author updatedAuthor = authorRepository.save(existingAuthor);

        return authorMapper.toDto(updatedAuthor);
    }

    @Override
    public void deleteById(Long id) {
        Long authorToDeleteId = findById(id).getId();

        authorRepository.deleteById(authorToDeleteId);
    }
}