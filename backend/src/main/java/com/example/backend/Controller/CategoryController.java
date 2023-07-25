package com.example.backend.Controller;

import com.example.backend.DTO.CategoryDTO;
import com.example.backend.Service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryData){
        return new ResponseEntity<>(categoryService.saveCategory(categoryData), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(categoryService.getAll());
    }
}