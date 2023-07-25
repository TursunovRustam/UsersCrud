package com.example.backend.Service.IncomeProduct;

import com.example.backend.Entity.IncomeProduct;
import com.example.backend.Entity.Product;
import com.example.backend.DTO.IncomeProductDTO;
import com.example.backend.Payload.IncomeProductPatchData;
import com.example.backend.Repo.IncomeProductRepo;
import com.example.backend.Repo.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeProductServiceImpl implements IncomeProductService {
    private final IncomeProductRepo incomeProductRepo;
    private final ProductRepo productRepo;

    @Override
    public UUID incomeProduct(IncomeProductDTO incomeProductData) {
        Product product = productRepo.findById(incomeProductData.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + incomeProductData.getProductId()));
        IncomeProduct savedIncomeProduct = incomeProductRepo.save(new IncomeProduct(
                null,
                product,
                incomeProductData.getIncomePrice(),
                incomeProductData.getCount(),
                LocalDateTime.now()
        ));
        return savedIncomeProduct.getId();
    }

    @Override
    public Integer setBalance(IncomeProductPatchData productData) {
        IncomeProduct incomeProduct = incomeProductRepo.findById(productData.getId())
                .orElseThrow(() -> new IllegalArgumentException("IncomeProduct not found with id: " + productData.getId()));
        incomeProduct.setCount(productData.getData());
        IncomeProduct savedIncomeProduct = incomeProductRepo.save(incomeProduct);
        return savedIncomeProduct.getCount();
    }
}