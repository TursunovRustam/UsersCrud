package com.example.backend.Controller;

import com.example.backend.Entity.ExpenseProduct;
import com.example.backend.Service.ExpenseProduct.ExpenseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenseProduct")
public class ExpenseProductsController {
    @Autowired
    public ExpenseProductService expenseProductService;

    @PostMapping
    public ResponseEntity<?> saveAllOrders(@RequestBody List<ExpenseProduct> expenseProduct){
        expenseProductService.saveAll(expenseProduct);
        return ResponseEntity.ok().build();
    }
}