package com.example.backend.Service.Category;

import com.example.backend.DTO.CategoryDTO;
import com.example.backend.Entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category saveCategory(CategoryDTO category);
}
