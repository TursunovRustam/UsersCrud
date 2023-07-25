package com.example.backend.Controller;

import com.example.backend.API.PrinterUse;
import com.example.backend.DTO.FilterDTO;
import com.example.backend.DTO.PrinterDTO;
import com.example.backend.DTO.ProductDTO;
import com.example.backend.Service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductCard(@PathVariable String productId){
        return ResponseEntity.ok(productService.getProductCard(productId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ROLE_CASHIER')")
    @GetMapping("/getAllCards")
    public ResponseEntity<?> getAllCards(){
        return ResponseEntity.ok(productService.getProductCardOfAllProduct());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productData){
        return new ResponseEntity<>(productService.saveProduct(productData), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("/print")
    public ResponseEntity<?> print(@RequestBody PrinterDTO printerDTO) throws PrintException {
        // Your printing logic here...
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PatchMapping
    public ResponseEntity<?> editProduct(@RequestBody ProductDTO productData){
        return ResponseEntity.ok(productService.updateProductData(productData));
    }

    @PatchMapping("/getByFilter")
    public ResponseEntity<?> getByFilter(@RequestBody FilterDTO filterDTO){
        return ResponseEntity.ok(productService.getProductsByFilter(filterDTO));
    }
}