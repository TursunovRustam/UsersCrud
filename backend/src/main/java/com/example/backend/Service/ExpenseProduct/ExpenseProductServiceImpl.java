package com.example.backend.Service.ExpenseProduct;

import com.example.backend.Entity.ExpenseProduct;
import com.example.backend.Entity.Product;
import com.example.backend.Repo.ExpenseProductsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseProductServiceImpl implements ExpenseProductService{
    public final ExpenseProductsRepo expenseProductsRepo;
    @Override
    public List<ExpenseProduct> saveAll(List<ExpenseProduct> expenseProduct) {
        return expenseProductsRepo.saveAll(expenseProduct);
    }
}
