package com.example.backend.Service.ExpenseProduct;

import com.example.backend.Entity.ExpenseProduct;
import com.example.backend.Entity.Product;
import com.example.backend.Repo.ExpenseProductsRepo;
import com.example.backend.Service.ExpenseProduct.ExpenseProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class ExpenseProductServiceImplTest {
    @Mock
    private ExpenseProductsRepo expenseProductsRepo;
    private ExpenseProductServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ExpenseProductServiceImpl(expenseProductsRepo);
    }

    @Test
    void itShouldSaveAllExpenseProducts() {
        // Mocked input data
        List<ExpenseProduct> mockedExpenseProducts = Arrays.asList(
                new ExpenseProduct(null, new Product(), 10, LocalDateTime.now()),
                new ExpenseProduct(null, new Product(), 20, LocalDateTime.now())
        );
        Mockito.when(expenseProductsRepo.saveAll(Mockito.anyIterable())).thenReturn(mockedExpenseProducts);

        // Calling the method under test
        List<ExpenseProduct> result = underTest.saveAll(mockedExpenseProducts);

        // Assertions
        Assertions.assertEquals(mockedExpenseProducts, result);
        Mockito.verify(expenseProductsRepo).saveAll(Mockito.anyIterable());
    }
}
