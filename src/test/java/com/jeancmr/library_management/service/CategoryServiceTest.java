package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.dto.Category.CategoryDto;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.CategoryMapper;
import com.jeancmr.library_management.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;
    private CategoryDto categoryDto1;
    private CategoryDto categoryDto2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(10L);
        category1.setName("Programming");
        category1.setDescription("Programming books");

        categoryDto1 = new CategoryDto();
        categoryDto1.setId(10L);
        categoryDto1.setName("Programming");
        categoryDto1.setDescription("Programming books");

        category2 = new Category();
        category2.setId(20L);
        category2.setName("Fiction");
        category2.setDescription("Fiction books");

        categoryDto2 = new CategoryDto();
        categoryDto2.setId(20L);
        categoryDto2.setName("Fiction");
        categoryDto2.setDescription("Fiction books");
    }

    @Test
    @DisplayName("Should return all CategoriesDto")
    void shouldReturnAllCategoriesDto() {
        List<Category> categoryList = List.of(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.toDto(category1)).thenReturn(categoryDto1);
        when(categoryMapper.toDto(category2)).thenReturn(categoryDto2);

        List<CategoryDto> result = categoryService.findAll();

        assertEquals(categoryList.size(), result.size());

        assertEquals(categoryDto1, result.get(0));
        assertEquals(categoryDto2, result.get(1));

        verify(categoryRepository).findAll();
        verify(categoryMapper).toDto(category1);
        verify(categoryMapper).toDto(category2);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("Should return an empty list when there are no categories")
    void shouldReturnEmptyListWhenThereAreNoCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryDto> result = categoryService.findAll();

        assertTrue(result.isEmpty());

        verify(categoryRepository).findAll();
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("Should return a categoryDto when Category exists")
    void shouldReturnCategoryDtoWhenCategoryExists() {
        // Arrange
        Long id = category1.getId();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category1));
        when(categoryMapper.toDto(category1)).thenReturn(categoryDto1);

        // Act
        CategoryDto result = categoryService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(category1.getName(), result.getName());
        assertEquals(category1.getDescription(), result.getDescription());

        verify(categoryRepository,times(1)).findById(id);
        verify(categoryMapper).toDto(category1);
        verifyNoMoreInteractions(categoryRepository,categoryMapper);
    }

    @Test
    @DisplayName("Should throw Exception when Category does not exist")
    void shouldThrowExceptionWhenCategoryDoesNotExist() {
        // Arrange
        Long id = category2.getId();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.findById(id));

        // Assert
        assertEquals("Category with ID '" + id + "' not found", exception.getMessage());

        verify(categoryRepository,times(1)).findById(id);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("Should save successfully Category when category name does not exists and return categoryDto")
    void shouldSaveCategoryDtoWhenCategoryNameDoesNotExistsAndReturnCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Programming");
        categoryDto.setDescription("Programming books");

        Category category = new Category();
        category.setName("Programming");
        category.setDescription("Programming books");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Programming");
        savedCategory.setDescription("Programming books");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(1L);
        expectedDto.setName("Programming");
        expectedDto.setDescription("Programming books");

        when(categoryRepository.existsByName(categoryDto.getName()))
                .thenReturn(false);

        when(categoryMapper.toEntity(categoryDto))
                .thenReturn(category);

        when(categoryRepository.save(category))
                .thenReturn(savedCategory);

        when(categoryMapper.toDto(savedCategory))
                .thenReturn(expectedDto);

        CategoryDto result = categoryService.save(categoryDto);

        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getDescription(), result.getDescription());

        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryMapper).toEntity(categoryDto);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(savedCategory);
        verifyNoMoreInteractions(categoryRepository,categoryMapper);
    }

    @Test
    @DisplayName("Should Throw Exception when Category name already exists")
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Programming");
        categoryDto.setDescription("Programming books");

        when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> categoryService.save(categoryDto)
        );

        assertEquals("Category with name '" + categoryDto.getName()
                + "' already exists", exception.getMessage());

        verify(categoryRepository).existsByName(categoryDto.getName());
        verify(categoryRepository, never()).save(any());
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("Should update successfully Category when Category exists and return CategoryDto")
    void shouldUpdateCategorySuccessfullyWhenCategoryExistsAndReturnCategoryDto() {
        Long id = 1L;

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Updated Name");
        categoryDto.setDescription("Updated description");

        CategoryDto existingDto = new CategoryDto();
        existingDto.setId(id);
        existingDto.setName("Old Name");
        existingDto.setDescription("Old description");

        Category existingCategory = new Category();
        existingCategory.setId(id);
        existingCategory.setName("Old Name");
        existingCategory.setDescription("Old description");

        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName("Updated Name");
        updatedCategory.setDescription("Updated description");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(id);
        expectedDto.setName("Updated Name");
        expectedDto.setDescription("Updated description");

        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(existingCategory));

        when(categoryMapper.toDto(existingCategory))
                .thenReturn(existingDto);

        when(categoryMapper.toEntity(existingDto))
                .thenReturn(existingCategory);

        doNothing()
                .when(categoryMapper)
                .update(categoryDto, existingCategory);

        when(categoryRepository.save(existingCategory))
                .thenReturn(updatedCategory);

        when(categoryMapper.toDto(updatedCategory))
                .thenReturn(expectedDto);

        CategoryDto result = categoryService.update(id, categoryDto);

        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getDescription(), result.getDescription());

        verify(categoryRepository).findById(id);
        verify(categoryMapper).toDto(existingCategory);
        verify(categoryMapper).toEntity(existingDto);
        verify(categoryMapper).update(categoryDto, existingCategory);
        verify(categoryRepository).save(existingCategory);
        verify(categoryMapper).toDto(updatedCategory);
        verifyNoMoreInteractions(categoryRepository,categoryMapper);
    }

    @Test
    @DisplayName("Should Throw Exception when trying to update a Category that does not exist")
    void shouldThrowExceptionWhenTryingToUpdateCategoryThatDoesNotExist() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.update(id, new CategoryDto()));

        assertEquals("Category with ID '" + id + "' not found", exception.getMessage());

        verify(categoryRepository).findById(id);
        verifyNoInteractions(categoryMapper);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    @DisplayName("Should delete Category successfully when category exists")
    void shouldDeleteCategorySuccessfullyWhenCategoryExists() {
        Long id = 10L;

        Category category = new  Category();
        category.setId(id);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        categoryService.deleteById(id);

        verify(categoryRepository).findById(id);
        verify(categoryMapper).toDto(category);
        verify(categoryRepository).deleteById(id);
        verifyNoMoreInteractions(categoryRepository,categoryMapper);
    }
}