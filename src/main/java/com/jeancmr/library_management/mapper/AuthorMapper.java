package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.dto.Author.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(Author author);
    Author toEntity(AuthorDto authorDto);

    void update(AuthorDto authorDto, @MappingTarget Author author);
}