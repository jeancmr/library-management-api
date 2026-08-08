package com.jeancmr.library_management.mapper;


import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.dto.Category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);

    void update(CategoryDto categoryDto, @MappingTarget Category category);
}
