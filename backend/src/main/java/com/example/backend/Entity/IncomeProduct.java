package com.example.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "income_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Product product;
    private Integer incomePrice;
    private Integer count;
    private LocalDateTime createdAt;
}
