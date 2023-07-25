package com.example.backend.Service.IncomeProduct;

import com.example.backend.Entity.IncomeProduct;
import com.example.backend.Entity.Product;
import com.example.backend.DTO.IncomeProductDTO;
import com.example.backend.Payload.IncomeProductPatchData;
import com.example.backend.Repo.IncomeProductRepo;
import com.example.backend.Repo.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class IncomeProductServiceImplTest {
    private IncomeProductRepo incomeProductRepo;
    private ProductRepo productRepo;
    private IncomeProductServiceImpl underTest;

    @BeforeEach
    void setUp() {
        incomeProductRepo = mock(IncomeProductRepo.class);
        productRepo = mock(ProductRepo.class);
        underTest = new IncomeProductServiceImpl(incomeProductRepo, productRepo);
    }

    @Test
    void itShouldIncomeProduct() {
        // Mocked input data
        UUID productId = UUID.randomUUID();
        IncomeProductDTO incomeProductDTO = new IncomeProductDTO(productId, 10, 5);

        // Mocked entities
        Product product = new Product();
        IncomeProduct savedIncomeProduct = new IncomeProduct(UUID.randomUUID(), product, 10, 5, LocalDateTime.now());

        // Stubbing the repository methods
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(incomeProductRepo.save(any(IncomeProduct.class))).thenReturn(savedIncomeProduct);

        // Calling the method under test
        UUID result = underTest.incomeProduct(incomeProductDTO);

        // Assertions
        Assertions.assertEquals(savedIncomeProduct.getId(), result);

        // Verifying the repository methods were called
        verify(productRepo).findById(productId);
        verify(incomeProductRepo).save(any(IncomeProduct.class));
    }

    @Test
    void itShouldSetBalance() {
        // Mocked input data
        UUID incomeProductId = UUID.randomUUID();
        Integer newBalance = 7;
        IncomeProductPatchData productData = new IncomeProductPatchData(incomeProductId, newBalance);

        // Mocked entity
        IncomeProduct incomeProduct = new IncomeProduct(incomeProductId, null, null, null, null);
        IncomeProduct savedIncomeProduct = new IncomeProduct(incomeProductId, null, null, newBalance, null);

        // Stubbing the repository methods
        when(incomeProductRepo.findById(incomeProductId)).thenReturn(Optional.of(incomeProduct));
        when(incomeProductRepo.save(any(IncomeProduct.class))).thenReturn(savedIncomeProduct);

        // Calling the method under test
        Integer result = underTest.setBalance(productData);

        // Assertions
        Assertions.assertEquals(savedIncomeProduct.getCount(), result);

        // Verifying the repository methods were called
        verify(incomeProductRepo).findById(incomeProductId);
        verify(incomeProductRepo).save(any(IncomeProduct.class));
    }
}
