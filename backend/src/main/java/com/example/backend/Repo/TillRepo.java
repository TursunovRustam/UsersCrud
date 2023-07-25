package com.example.backend.Repo;

import com.example.backend.Entity.Till;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TillRepo extends JpaRepository<Till, UUID> {
}
