package com.example.products.controller;

import com.example.products.dto.CategoryRequest;
import com.example.products.dto.CategoryResponse;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.model.Category;
import com.example.products.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController 
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repoCategory;

    public CategoryController(CategoryRepository repoCategory){
        this.repoCategory = repoCategory;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest category) {
        Category entity = toEntity(category);
        Category savedCategory = repoCategory.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedCategory));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> list = new ArrayList<>();
        for (Category elem : repoCategory.findAll()) {
            list.add(toResponse(elem));
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        Category category = repoCategory.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
        return ResponseEntity.ok(toResponse(category));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryUpdated) {
        if (!repoCategory.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        Category entity = toEntity(categoryUpdated);
        entity.setId(id); 
        Category savedCategory = repoCategory.save(entity);
        return ResponseEntity.ok(toResponse(savedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repoCategory.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        repoCategory.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

    private Category toEntity(CategoryRequest category) {
        Category categoryEntity = new Category();
        categoryEntity.setName(category.name());
        return categoryEntity;
    }
}
