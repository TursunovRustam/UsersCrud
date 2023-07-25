package com.example.backend.Service.ExpenseProduct;

import com.example.backend.Entity.ExpenseProduct;
import com.example.backend.Entity.Product;

import java.util.List;

public interface ExpenseProductService {
    List<ExpenseProduct> saveAll(List<ExpenseProduct> expenseProduct);
}
