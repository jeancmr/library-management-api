package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.dto.Category.CategoryDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    Category findEntityById(Long id);
    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(Long id,CategoryDto categoryDto);
    void deleteById(Long id);

}
