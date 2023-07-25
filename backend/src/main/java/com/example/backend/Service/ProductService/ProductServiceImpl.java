    package com.example.backend.Service.ProductService;

    import com.example.backend.DTO.FilterDTO;
    import com.example.backend.DTO.ProductDTO;
    import com.example.backend.Entity.AvatarImg;
    import com.example.backend.Entity.Category;
    import com.example.backend.Entity.IncomeProduct;
    import com.example.backend.Entity.Product;
    import com.example.backend.Payload.SearchData;
    import com.example.backend.Projection.ProductProjection;
    import com.example.backend.Projection.ProductProjectionGetAll;
    import com.example.backend.Repo.AvatarImgRepo;
    import com.example.backend.Repo.CategoryRepo;
    import com.example.backend.Repo.IncomeProductRepo;
    import com.example.backend.Repo.ProductRepo;
    import lombok.AllArgsConstructor;
    import org.apache.el.stream.Optional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.parameters.P;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.UUID;

    @Service
    @AllArgsConstructor
    public class ProductServiceImpl implements ProductService {
        @Autowired
        private ProductRepo productRepo;
        @Autowired
        private CategoryRepo categoryRepo;
        @Autowired
        private IncomeProductRepo incomeProductRepo;
        @Autowired
        private AvatarImgRepo imgRepo;
        @Override
        public Product saveProduct(ProductDTO productData) {
            Category category = categoryRepo.findById(productData.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productData.getCategoryId()));
            AvatarImg avatarImg = imgRepo.findById(productData.getImgId())
                    .orElseThrow(() -> new IllegalArgumentException("AvatarImg not found with id: " + productData.getImgId()));
            return productRepo.save(new Product(null, productData.getName(), category, productData.getCode(), productData.getPrice(), avatarImg));
        }
        @Override
        public List<Product> getProducts() {
            return productRepo.findAll();
        }
        @Override
        public ProductProjection getProductCard(String incomeId) {
            return productRepo.getProductCard(UUID.fromString(incomeId));
        }
        @Override
        public List<ProductProjectionGetAll> getProductCardOfAllProduct() {
            return productRepo.getCountOfAllProducts();
        }
        @Override
        public Product updateProductData(ProductDTO productDTO) {
            Product existingProduct = findProductByCode(productDTO.getCode());
            IncomeProduct incomeProduct = createOrUpdateIncomeProduct(existingProduct, productDTO);
            incomeProductRepo.save(incomeProduct);
            updateProductDetails(existingProduct, productDTO);
            return productRepo.save(existingProduct);
        }

        private Product findProductByCode(String code) {
            Product existingProduct = productRepo.findByCode(code);
            if (existingProduct == null) {
                throw new IllegalArgumentException("Product not found with code: " + code);
            }
            return existingProduct;
        }

        private IncomeProduct createOrUpdateIncomeProduct(Product existingProduct, ProductDTO productDTO) {
            IncomeProduct incomeProduct = incomeProductRepo.findByProductId(existingProduct.getId());
            incomeProduct.setProduct(existingProduct);
            incomeProduct.setIncomePrice(productDTO.getIncomePrice());
            incomeProduct.setCount(productDTO.getBalance());
            incomeProduct.setCreatedAt(LocalDateTime.now());
            return incomeProduct;
        }

        private void updateProductDetails(Product existingProduct, ProductDTO productDTO) {
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setCode(productDTO.getCode());
        }
        @Override
        public List<Product> searchProducts(SearchData searchData) {
            return productRepo.findAllByNameContainingIgnoreCaseOrCodeOrCategory_Id(searchData.getName(), searchData.getCode(), searchData.getCategoryId());
        }
        @Override
        public List<ProductProjectionGetAll> getProductsByFilter(FilterDTO filterDTO) {
            List<ProductProjectionGetAll> productList = new ArrayList<>();
            if (filterDTO.getCategoryId() != null) {
                productList = retrieveProductsByCategoryAndName(filterDTO);
            } else if (!filterDTO.getSearch().isEmpty()) {
                productList = retrieveProductsByName(filterDTO);
            } else {
                productList = productRepo.getCountOfAllProducts();
            }
            return productList;
        }

        private List<ProductProjectionGetAll> retrieveProductsByCategoryAndName(FilterDTO filterDTO) {
            List<ProductProjectionGetAll> productList = productRepo.findAllByCategory_IdAndNameContainingIgnoreCase(
                    filterDTO.getCategoryId(), filterDTO.getSearch()
            );
            if (productList.isEmpty()) {
                productList = retrieveProductsByCategoryAndNameSimilarity(filterDTO, 0.4);
                if (productList.isEmpty()) {
                    productList = retrieveProductsByCategoryAndNameSimilarity(filterDTO, 0.1);
                }
            }
            return productList;
        }
        List<ProductProjectionGetAll> retrieveProductsByCategoryAndNameSimilarity(FilterDTO filterDTO, double similarity) {
            return productRepo.findAllByCategory_IdAndNameContainingIgnoreCaseSimilarity(
                    filterDTO.getCategoryId(), filterDTO.getSearch(), similarity
            );
        }
        List<ProductProjectionGetAll> retrieveProductsByName(FilterDTO filterDTO) {
            List<ProductProjectionGetAll> productList = productRepo.findAllByNameContainingIgnoreCase(filterDTO.getSearch());
            if (productList.isEmpty()) {
                productList = retrieveProductsByNameSimilarity(filterDTO, 0.4);
                if (productList.isEmpty()) {
                    productList = retrieveProductsByNameSimilarity(filterDTO, 0.1);
                }
            }
            return productList;
        }
        private List<ProductProjectionGetAll> retrieveProductsByNameSimilarity(FilterDTO filterDTO, double similarity) {
            return productRepo.findAllByNameSimilarity(filterDTO.getSearch(), similarity);
        }
    }