package com.example.backend.Repo;

import com.example.backend.Entity.Category;
import com.example.backend.Entity.IncomeProduct;
import com.example.backend.Entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IncomeProductRepoTest {
    @Autowired
    private IncomeProductRepo incomeProductRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;
    private Category fastFood;
    private Product burger;
    private IncomeProduct savedIncomeProduct;
    private String productId = "";


    @BeforeEach
    void generateProduct(){
        ;
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
        productId = burger.getId().toString();
        savedIncomeProduct = incomeProductRepo.save(new IncomeProduct(
                UUID.fromString("ac5f6673-7808-47a9-8062-6442d1d17550"),
                burger,
                10000,
                100,
                LocalDateTime.now()
        ));
    }

    @Test
    void itShouldFindByProductId() {
        IncomeProduct incomeProduct = incomeProductRepo.findByProductId(UUID.fromString(productId));
        assertEquals(burger, incomeProduct.getProduct());
    }
}