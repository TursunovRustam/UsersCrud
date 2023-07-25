package com.example.backend.Repo;

import com.example.backend.Entity.IncomeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomeProductRepo extends JpaRepository<IncomeProduct, UUID> {
    IncomeProduct findByProductId(UUID productId);
}
