package com.example.backend.Service.Till;

import com.example.backend.Entity.Till;
import com.example.backend.Repo.TillRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class TillServiceImplTest {

    @Mock
    private TillRepo tillRepo;

    private TillServiceImpl tillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tillService = new TillServiceImpl(tillRepo);
    }

    @Test
    void getAllTills_ShouldReturnAllTills() {
        // Arrange
        List<Till> expectedTills = new ArrayList<>();
        when(tillRepo.findAll()).thenReturn(expectedTills);

        // Act
        List<Till> result = tillService.getAllTills();

        // Assert
        Assertions.assertEquals(expectedTills, result);
        verify(tillRepo, times(1)).findAll();
    }

    @Test
    void saveTill_ShouldSaveTill() {
        // Arrange
        Till till = new Till();
        when(tillRepo.save(till)).thenReturn(till);

        // Act
        Till result = tillService.saveTill(till);

        // Assert
        Assertions.assertEquals(till, result);
        verify(tillRepo, times(1)).save(till);
    }

    @Test
    void getTillById_ExistingId_ShouldReturnTill() {
        // Arrange
        UUID id = UUID.randomUUID();
        Till expectedTill = new Till();
        when(tillRepo.findById(id)).thenReturn(Optional.of(expectedTill));

        // Act
        Till result = tillService.getTillById(id);

        // Assert
        Assertions.assertEquals(expectedTill, result);
        verify(tillRepo, times(1)).findById(id);
    }

    @Test
    void getTillById_NonExistingId_ShouldThrowException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(tillRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> tillService.getTillById(id));
        verify(tillRepo, times(1)).findById(id);
    }
}
