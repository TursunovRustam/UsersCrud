package com.example.backend.Repo;

import com.example.backend.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
