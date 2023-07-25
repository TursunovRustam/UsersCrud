package com.example.backend.Projection;


import com.example.backend.Entity.Category;
import jakarta.persistence.Lob;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ProductProjection {
    @Value("#{target.name}")
    String getName();
    @Value("#{target.code}")
    String getCode();
    @Value("#{target.price}")
    Integer getPrice();
    @Value("#{target.income_price}")
    Integer getIncome_price();
    @Value("#{target.img_id}")
    UUID getImg_id();
    @Value("#{target.balance}")
    Integer getBalance();
}
