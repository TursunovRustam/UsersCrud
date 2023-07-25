package com.example.backend.Service.QRCodeService;

import com.example.backend.Projection.ProductProjectionGetAll;
import com.example.backend.Repo.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class QRCodeServiceImplTest {
    private ProductRepo productRepo;
    private QRCodeServiceImpl underTest;

    @BeforeEach
    void setUp() {
        productRepo = mock(ProductRepo.class);
        underTest = new QRCodeServiceImpl(productRepo);
    }

    @Test
    void itShouldGetProductByCode() {
        // Mocked input data
        String code = "ABC123";

        // Mocked projection
        ProductProjectionGetAll productProjection = mock(ProductProjectionGetAll.class);

        // Stubbing the repository method
        when(productRepo.getByCode(code)).thenReturn(productProjection);

        // Calling the method under test
        ProductProjectionGetAll result = underTest.getProductByCode(code);

        // Assertions
        Assertions.assertEquals(productProjection, result);

        // Verifying the repository method was called
        verify(productRepo).getByCode(code);
    }
}
