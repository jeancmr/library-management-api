package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.dto.CategoryDto;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.CategoryMapper;
import com.jeancmr.library_management.repository.CategoryRepository;
import com.jeancmr.library_management.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper  categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(Category.class, id));
        return categoryMapper.toDto(foundCategory);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);

        return  categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category existingCategory= categoryMapper.toEntity(findById(id));
        categoryMapper.update(categoryDto, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);

        return  categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        Long categoryToDeleteId = findById(id).getId();

        categoryRepository.deleteById(categoryToDeleteId);
    }
}
