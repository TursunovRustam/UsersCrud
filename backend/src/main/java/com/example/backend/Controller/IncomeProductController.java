package com.example.backend.Controller;

import com.example.backend.DTO.IncomeProductDTO;
import com.example.backend.Service.IncomeProduct.IncomeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income-product")
public class IncomeProductController {
    @Autowired
    private IncomeProductService incomeProductService;

    @PostMapping
    public ResponseEntity<?> incomeProduct(@RequestBody IncomeProductDTO incomeProductData){
        return new ResponseEntity<>(incomeProductService.incomeProduct(incomeProductData), HttpStatus.CREATED);
    }

    @PatchMapping("{type}")
    public ResponseEntity<?> editProduct(@PathVariable String type, @RequestBody  IncomeProductDTO incomeProductData){
        if (type.equals("balance")){
            return ResponseEntity.ok(incomeProductService.incomeProduct(incomeProductData));
        }
        return ResponseEntity.notFound().build();
    }
}