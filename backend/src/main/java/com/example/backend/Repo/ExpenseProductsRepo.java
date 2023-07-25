package com.example.backend.Repo;

import com.example.backend.Entity.ExpenseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseProductsRepo extends JpaRepository<ExpenseProduct, UUID> {
}
