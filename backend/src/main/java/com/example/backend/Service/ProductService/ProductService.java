package com.example.backend.Service.ProductService;

import com.example.backend.DTO.FilterDTO;
import com.example.backend.DTO.ProductDTO;
import com.example.backend.Entity.Product;
import com.example.backend.Payload.SearchData;
import com.example.backend.Projection.ProductProjection;
import com.example.backend.Projection.ProductProjectionGetAll;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDTO productData);
    List<Product> getProducts();
    ProductProjection getProductCard(String id);
    List<ProductProjectionGetAll> getProductCardOfAllProduct();
    Product updateProductData(ProductDTO productDTO);
    List<Product> searchProducts(SearchData searchData);
    List<ProductProjectionGetAll> getProductsByFilter(FilterDTO filterDTO);
}
