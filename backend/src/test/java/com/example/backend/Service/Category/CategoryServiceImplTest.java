package com.example.backend.Service.Category;

import com.example.backend.DTO.CategoryDTO;
import com.example.backend.Entity.Category;
import com.example.backend.Repo.CategoryRepo;
import com.example.backend.Service.Category.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepo categoryRepo;
    private CategoryServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CategoryServiceImpl(categoryRepo);
    }

    @Test
    void itShouldGetAllCategories() {
        // Mocked input data
        List<Category> mockedCategories = Arrays.asList(
                new Category(null, "Category 1"),
                new Category(null, "Category 2")
        );
        Mockito.when(categoryRepo.findAll()).thenReturn(mockedCategories);

        // Calling the method under test
        List<Category> result = underTest.getAll();

        // Assertions
        Assertions.assertEquals(mockedCategories, result);
    }

    @Test
    void itShouldSaveCategory() {
        // Mocked input data
        CategoryDTO categoryDTO = new CategoryDTO("New Category");
        Category mockedCategory = new Category(null, "New Category");
        Mockito.when(categoryRepo.save(Mockito.any(Category.class))).thenReturn(mockedCategory);

        // Calling the method under test
        Category result = underTest.saveCategory(categoryDTO);

        // Assertions
        Assertions.assertEquals(mockedCategory, result);
    }
}
