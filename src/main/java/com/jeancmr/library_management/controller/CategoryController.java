package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.Category.CategoryDto;
import com.jeancmr.library_management.service.interfaces.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Operations related to categories")
public class CategoryController {
    private final ICategoryService  categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN', 'ROLE_MEMBER')")
    @Operation(summary = "Getting all categories")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Categories found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<List<CategoryDto>> findAllCategories(){
        List<CategoryDto> categoryList = categoryService.findAll();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting category by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Category found"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable Long id){
        CategoryDto categoryDtoFound = categoryService.findById(id);
        return ResponseEntity.ok(categoryDtoFound);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Save category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Category saved successfully"),
                    @ApiResponse(responseCode = "409", description = "Category already registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDtoFound = categoryService.save(categoryDto);
        return new  ResponseEntity<>(categoryDtoFound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Update category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        CategoryDto categoryUpdated = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(categoryUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Delete category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
