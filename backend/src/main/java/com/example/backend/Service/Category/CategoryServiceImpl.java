package com.example.backend.Service.Category;

import com.example.backend.DTO.CategoryDTO;
import com.example.backend.Entity.Category;
import com.example.backend.Repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    public final CategoryRepo categoryRepo;
    @Override
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
    @Override
    public Category saveCategory(CategoryDTO category) {
        return categoryRepo.save(new Category(null, category.getName()));
    }
}