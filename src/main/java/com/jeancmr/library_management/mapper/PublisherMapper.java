package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.Publisher;
import com.jeancmr.library_management.dto.PublisherDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherDto toDto(Publisher publisher);
    Publisher toEntity(PublisherDto publisherDto);

    void update(PublisherDto publisherDto, @MappingTarget Publisher publisher);
}