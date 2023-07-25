package com.example.backend.Service.IncomeProduct;

import com.example.backend.DTO.IncomeProductDTO;
import com.example.backend.Payload.IncomeProductPatchData;

import java.util.UUID;

public interface IncomeProductService {
    UUID incomeProduct(IncomeProductDTO incomeProductData);
    Integer setBalance(IncomeProductPatchData productData);
}