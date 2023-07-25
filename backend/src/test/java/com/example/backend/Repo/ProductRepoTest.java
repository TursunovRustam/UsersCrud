package com.example.backend.Repo;

import com.example.backend.Entity.Category;
import com.example.backend.Entity.Product;
import com.example.backend.Projection.ProductProjection;
import com.example.backend.Projection.ProductProjectionGetAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepoTest {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    private String nameBurger = "";
    private String code = "";
    private String categoryId = "";
    private String id = "120a0c7f-e4bf-4844-8de6-9003012e1960";
    private Category fastFood;
    private Product burger;

    @BeforeEach
    void generateProduct(){
        nameBurger = "Brgr";
        code = "10101";
        categoryId = "bae694fd-52cf-41f0-860f-ee9f87e7d588";
        fastFood = categoryRepo.save(new Category(
                UUID.fromString("bae694fd-52cf-41f0-860f-ee9f87e7d588"),
                "FastFood"
        ));
        burger = productRepo.save(new Product(
                UUID.fromString("120a0c7f-e4bf-4844-8de6-9003012e1960"),
                "Brgr",
                fastFood,
                "10101",
                1000,
                null
        ));
    }

    @Test
    void itShouldFindAllByNameContainingIgnoreCaseOrCodeOrCategory_Id() {
        List<Product> result = productRepo.findAllByNameContainingIgnoreCaseOrCodeOrCategory_Id(nameBurger, code, UUID.fromString(categoryId));
        assertEquals(burger, result.get(0));
    }

    @Test
    void itShouldGetProductCard() {
        id = "120a0c7f-e4bf-4844-8de6-9003012e1960";
        Category fastFood = categoryRepo.save(new Category(
                UUID.fromString("bae694fd-52cf-41f0-860f-ee9f87e7d588"),
                "FastFood"
        ));
        Product expectedProduct = productRepo.save(new Product(
                UUID.fromString("120a0c7f-e4bf-4844-8de6-9003012e1960"),
                "Brgr",
                fastFood,
                "10101",
                1000,
                null
        ));
        Assertions.assertNotEquals(null, expectedProduct);
    }

    @Test
    void itShouldGetCountOfAllProducts() {
        List<ProductProjectionGetAll> countOfAllProducts = productRepo.getCountOfAllProducts();
        Assertions.assertNotEquals(null, countOfAllProducts);
    }


    @Test
    void itShouldGetByCode() {
        ProductProjectionGetAll actualProductProjection = productRepo.getByCode(code);
        Assertions.assertEquals(null, actualProductProjection);
    }

    @Test
    void itShouldFindByCode() {
        Product expectedProduct = productRepo.findByCode(code);
        assertEquals(burger, expectedProduct);
    }

    @Test
    void itShouldFindAllByCategory_IdAndNameContainingIgnoreCase() {
        List<ProductProjectionGetAll> allByCategoryIdAndNameContainingIgnoreCase = productRepo.findAllByCategory_IdAndNameContainingIgnoreCase(UUID.fromString(categoryId), nameBurger);
        Assertions.assertNotEquals(null, allByCategoryIdAndNameContainingIgnoreCase);
    }

    @Test
    void itShouldFindAllByCategory_Id() {
        List<ProductProjectionGetAll> allByCategoryId = productRepo.findAllByCategory_Id(UUID.fromString(categoryId));
        Assertions.assertNotEquals(null, allByCategoryId);
    }

    @Test
    void itShouldFindAllByNameContainingIgnoreCaseFirstStep() {
        List<ProductProjectionGetAll> allByNameContainingIgnoreCaseFirstStep = productRepo.findAllByCategory_IdAndNameContainingIgnoreCaseSimilarity(UUID.fromString(categoryId), nameBurger, 1.0);
        Assertions.assertNotEquals(null, allByNameContainingIgnoreCaseFirstStep);
    }


}