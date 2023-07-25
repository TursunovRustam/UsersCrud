package com.example.backend.DTO;

import com.example.backend.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private UUID categoryId;
    private String code;
    private Integer price;
    private Integer balance;
    private Integer incomePrice;
    private UUID imgId;
}
