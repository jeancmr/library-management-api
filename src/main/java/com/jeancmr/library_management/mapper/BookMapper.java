package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.dto.Book.BookRequestDto;
import com.jeancmr.library_management.dto.Book.BookResponseDto;
import com.jeancmr.library_management.dto.Book.BookResponseSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "copies", ignore = true)
    Book toEntity(BookRequestDto dto);

    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "categories", source = "categories")
    BookResponseDto toResponseDto(Book book);

    BookResponseSummaryDto toResponseSummaryDto(Book book);
}
