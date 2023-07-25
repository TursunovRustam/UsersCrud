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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private IncomeProductRepo incomeProductRepo;

    @Mock
    private AvatarImgRepo imgRepo;

    private ProductServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ProductServiceImpl(productRepo, categoryRepo, incomeProductRepo, imgRepo);
    }

    @Test
    void itShouldSaveProduct() {
        // Mocked input data
        UUID categoryId = UUID.randomUUID();
        UUID imgId = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(
                "Burger",
                categoryId,
                "10101",
                1000,
                1000,
                1000,
                imgId
        );

        // Mocking the Category and AvatarImg objects
        Category category = new Category(categoryId, "FastFood");
        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        AvatarImg avatarImg = new AvatarImg(imgId, null);
        when(imgRepo.findById(imgId)).thenReturn(Optional.of(avatarImg));

        // Mocking the saved product
        Product savedProduct = new Product(UUID.randomUUID(), "Burger", category, "10101", 1000, null);
        when(productRepo.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        // Calling the method under test
        Product result = underTest.saveProduct(productDTO);

        // Verifying the repository interactions
        verify(categoryRepo).findById(categoryId);
        verify(imgRepo).findById(imgId);
        verify(productRepo).save(Mockito.any(Product.class));

        // Assertions
        assertEquals(savedProduct, result);
    }

    @Test
    void itShouldGetProducts() {
        // Mocked product list
        List<Product> productList = Arrays.asList(
                new Product(UUID.randomUUID(), "Burger 1", new Category(UUID.randomUUID(), "FastFood"), "10101", 1000, null),
                new Product(UUID.randomUUID(), "Burger 2", new Category(UUID.randomUUID(), "FastFood"), "20202", 2000, null)
        );
        when(productRepo.findAll()).thenReturn(productList);

        // Calling the method under test
        List<Product> result = underTest.getProducts();

        // Verifying the repository interaction
        verify(productRepo).findAll();

        // Assertions
        assertEquals(productList, result);
    }

    @Test
    void itShouldGetProductCard() {
        // Mocked input data
        String incomeId = "5c12db17-ebae-48f6-9d13-e33db0282257";
        ProductProjection productProjection = Mockito.mock(ProductProjection.class);
        when(productRepo.getProductCard(UUID.fromString(incomeId))).thenReturn(productProjection);

        // Calling the method under test
        ProductProjection result = underTest.getProductCard(incomeId);

        // Verifying the repository interaction
        verify(productRepo).getProductCard(UUID.fromString(incomeId));

        // Assertions
        assertEquals(productProjection, result);
    }

    @Test
    void itShouldGetProductCardOfAllProduct() {
        // Mocked product projection list
        List<ProductProjectionGetAll> productList = Arrays.asList(
                Mockito.mock(ProductProjectionGetAll.class),
                Mockito.mock(ProductProjectionGetAll.class)
        );
        when(productRepo.getCountOfAllProducts()).thenReturn(productList);

        // Calling the method under test
        List<ProductProjectionGetAll> result = underTest.getProductCardOfAllProduct();

        // Verifying the repository interaction
        verify(productRepo).getCountOfAllProducts();

        // Assertions
        assertEquals(productList, result);
    }

    @Test
    void itShouldUpdateProductData() {
        // Mocked input data
        ProductDTO productDTO = new ProductDTO(
                "Burger",
                UUID.randomUUID(),
                "10101",
                1000,
                1000,
                1000,
                null
        );

        // Mocking the existing product
        Product existingProduct = new Product(
        UUID.randomUUID(),
                "Burger",
                new Category(UUID.randomUUID(), "FastFood"),
                "20202",
                2000,
                null
        );
        when(productRepo.findByCode(productDTO.getCode())).thenReturn(existingProduct);

        // Mocking the existing income product
        IncomeProduct existingIncomeProduct = new IncomeProduct();
        when(incomeProductRepo.findByProductId(existingProduct.getId())).thenReturn(existingIncomeProduct);

        // Mocking the updated product
        Product updatedProduct = new Product(
                existingProduct.getId(),
                productDTO.getName(),
                existingProduct.getCategory(),
                productDTO.getCode(),
                productDTO.getPrice(),
                existingProduct.getImgUrl()
        );
        when(productRepo.save(existingProduct)).thenReturn(updatedProduct);

        // Calling the method under test
        Product result = underTest.updateProductData(productDTO);

        // Verifying the repository interactions
        verify(productRepo).findByCode(productDTO.getCode());
        verify(incomeProductRepo).findByProductId(existingProduct.getId());
        verify(incomeProductRepo).save(existingIncomeProduct);
        verify(productRepo).save(existingProduct);

        // Assertions
        assertEquals(updatedProduct, result);
    }

    @Test
    void itShouldSearchProducts() {
        // Mocked input data
        SearchData searchData = new SearchData("Burger", "10101", UUID.randomUUID());

        // Mocked product list
        List<Product> productList = Arrays.asList(
                new Product(UUID.randomUUID(), "Burger 1", new Category(UUID.randomUUID(), "FastFood"), "10101", 1000, null),
                new Product(UUID.randomUUID(), "Burger 2", new Category(UUID.randomUUID(), "FastFood"), "20202", 2000, null)
        );
        when(productRepo.findAllByNameContainingIgnoreCaseOrCodeOrCategory_Id(
                searchData.getName(), searchData.getCode(), searchData.getCategoryId()))
                .thenReturn(productList);

        // Calling the method under test
        List<Product> result = underTest.searchProducts(searchData);

        // Verifying the repository interaction
        verify(productRepo).findAllByNameContainingIgnoreCaseOrCodeOrCategory_Id(
                searchData.getName(), searchData.getCode(), searchData.getCategoryId());

        // Assertions
        assertEquals(productList, result);
    }

    @Test
    void itShouldGetProductsByFilter() {
        // Mocked input data
        FilterDTO filterDTO = new FilterDTO(UUID.randomUUID(), "Burger");

        // Mocked product projection list
        List<ProductProjectionGetAll> productList = Arrays.asList(
                Mockito.mock(ProductProjectionGetAll.class),
                Mockito.mock(ProductProjectionGetAll.class)
        );

        // Mocking the repository methods based on the filter criteria
        if (filterDTO.getCategoryId() != null && !filterDTO.getSearch().isEmpty()) {
            when(productRepo.findAllByCategory_IdAndNameContainingIgnoreCase(
                    filterDTO.getCategoryId(), filterDTO.getSearch()))
                    .thenReturn(productList);
            when(productRepo.findAllByCategory_IdAndNameContainingIgnoreCaseSimilarity(
                    filterDTO.getCategoryId(), filterDTO.getSearch(), 0.4))
                    .thenReturn(productList);
            when(productRepo.findAllByCategory_IdAndNameContainingIgnoreCaseSimilarity(
                    filterDTO.getCategoryId(), filterDTO.getSearch(), 0.1))
                    .thenReturn(productList);
        } else if (filterDTO.getCategoryId() != null) {
            when(productRepo.findAllByCategory_Id(filterDTO.getCategoryId()))
                    .thenReturn(productList);
        } else if (!filterDTO.getSearch().isEmpty()) {
            when(productRepo.findAllByNameContainingIgnoreCase(filterDTO.getSearch()))
                    .thenReturn(productList);
            when(productRepo.findAllByNameSimilarity(filterDTO.getSearch(), 0.4))
                    .thenReturn(productList);
            when(productRepo.findAllByNameSimilarity(filterDTO.getSearch(), 0.1))
                    .thenReturn(productList);
        } else {
            when(productRepo.getCountOfAllProducts()).thenReturn(productList);
        }

        // Calling the method under test
        List<ProductProjectionGetAll> result = underTest.getProductsByFilter(filterDTO);

        // Assertions
        assertEquals(productList, result);
    }
    @Test
    void itShouldRetrieveProductsByCategoryAndNameSimilarity() {
        // Given
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setCategoryId(UUID.randomUUID());
        filterDTO.setSearch("test");
        double similarity = 0.4;
        Category category = new Category();
        category.setId(filterDTO.getCategoryId());
        category.setName("Test Category");
        Product product = new Product();
        product.setName("Test Product");
        product.setCategory(category);
        ProductProjectionGetAll productProjection = Mockito.mock(ProductProjectionGetAll.class);
        List<ProductProjectionGetAll> expectedProducts = new ArrayList<>();
        expectedProducts.add(productProjection);
        when(productRepo.findAllByCategory_IdAndNameContainingIgnoreCaseSimilarity(
                filterDTO.getCategoryId(), filterDTO.getSearch(), similarity)).thenReturn(expectedProducts);
        List<ProductProjectionGetAll> actualProducts = underTest.retrieveProductsByCategoryAndNameSimilarity(filterDTO, similarity);
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void itShouldRetrieveProductsByName() {
        // Given
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setSearch("test");

        // Create a list of ProductProjectionGetAll objects
        List<ProductProjectionGetAll> expectedProducts = new ArrayList<>();
        expectedProducts.add(Mockito.mock(ProductProjectionGetAll.class));

        // Mock productRepo.findAllByNameContainingIgnoreCase to return an empty list
        when(productRepo.findAllByNameContainingIgnoreCase(filterDTO.getSearch())).thenReturn(new ArrayList<>());

        // Mock productRepo.findAllByNameSimilarity to return the expectedProducts
        when(productRepo.findAllByNameSimilarity(filterDTO.getSearch(), 0.4)).thenReturn(new ArrayList<>());
        when(productRepo.findAllByNameSimilarity(filterDTO.getSearch(), 0.1)).thenReturn(expectedProducts);

        // When
        List<ProductProjectionGetAll> actualProducts = underTest.retrieveProductsByName(filterDTO);

        // Then
        assertEquals(expectedProducts, actualProducts);
    }


}
