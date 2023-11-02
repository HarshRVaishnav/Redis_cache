package com.example.service;

import com.example.entity.Product;
import com.example.exception.ProductNotFoundException;
import com.example.repository.IProductRepo;
import com.example.serviceimpl.ProductServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private IProductRepo productRepo;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    //**************************************************** Test createProduct*********************************************************************//


    @Test
    @Order(1)
    public void test_createProduct() {
        Product product = new Product();
        product.setPrice(100.00);
        product.setProductDescription("So nice product");
        product.setQuantityInStock(1);
        product.setProductName("Fan");
        when(productRepo.save(product)).thenReturn(product);
        Product result = productService.createProduct(product);
        assertEquals(product, result);
        verify(productRepo).save(product);

    }

    //**************************************************** Test createProductList*****************************************************************//


    @Test
    @Order(2)
    public void test_createProductList() {
        List<Product> expectedProducts = new LinkedList<>();
        expectedProducts.add(new Product(1, "Product 1", "good one", 2, 200.2));
        expectedProducts.add(new Product(2, "Product 2", "shoes", 1, 400.50));
        expectedProducts.add(new Product(3, "Product 3", "SmartPhone", 1, 9500.50));

        // Mock the repository's saveAll method to return the list of products
        when(productRepo.saveAll(expectedProducts)).thenReturn(expectedProducts);

        // Call the service method to save the list of products
        List<Product> savedProducts = productService.createProductList(expectedProducts);

        // Verify that the saveAll method was called with the correct argument
        verify(productRepo).saveAll(expectedProducts);

        // Verify that the saved products match the expected products
        assertEquals(expectedProducts, savedProducts);
    }


    //**************************************************** Test GetOneProduct*****************************************************************//

    @Test
    @Order(3)
    public void test_getOneProduct() {
        Product product = new Product();
        product.setProductCode(1);
        product.setPrice(100.00);
        product.setProductDescription("So nice product");
        product.setQuantityInStock(1);
        product.setProductName("Fan");
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        Product actualCustomer = productService.getProductById(1);
        assertEquals(1, actualCustomer.getProductCode());
    }


    //**************************************************** Test GetAllProducts*****************************************************************//

    @Test
    @Order(4)
    void getAllProducts_WhenProductsExist_ReturnsProductList() {
        // Arrange
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1, "Product 1", "good one", 2, 200.2));
        expectedProducts.add(new Product(2, "Product 2", "shoes", 1, 400.50));
        when(productRepo.count()).thenReturn(2L);
        when(productRepo.findAll()).thenReturn(expectedProducts);
        // Act
        List<Product> actualProducts = productService.getAllProducts();
        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepo, times(1)).findAll();
    }


    @Test
    void getAllProducts_WhenNoProductsExist_ThrowsProductNotFoundException() {
        // Arrange
        when(productRepo.count()).thenReturn(0L);
        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getAllProducts());
    }
    //**************************************************** Test DeleteProductById*****************************************************************//


    @Test
    @Order(5)
    public void testDeleteProductById() {
        // Arrange
        Integer productCode = 123;
        Product product = new Product();
        product.setProductCode(productCode);
        when(productRepo.findById(productCode)).thenReturn(Optional.of(product));

        // Act
        String result = productService.deleteProductById(productCode);

        // Assert
        verify(productRepo, times(1)).findById(productCode);
        verify(productRepo, times(1)).delete(product);
        //verify(cacheManager.getCache("customer")).clear();
        assertEquals("One product deleted with productCode: " + productCode, result);
    }


    //**************************************************** Test UpdateProduct*****************************************************************//

    @Test
    @Order(6)
    public void test_updateProduct() {
        Integer productCode = 1;
        Product product = new Product(productCode, "AC", "So nice Product", 1, 8000.20);

        Optional<Product> optional = Optional.of(product);

        Product updateProduct = new Product();
        updateProduct.setPrice(1000.00);
        updateProduct.setProductDescription("nice Product");
        updateProduct.setQuantityInStock(10);
        updateProduct.setProductName("Fridge");
        //updateProduct.setImageData(null);

        when(productRepo.findById(productCode)).thenReturn(optional);
        when(productRepo.save(product)).thenReturn(updateProduct);

        Product result = productService.updateProduct(productCode, product);

        assertEquals(updateProduct, result);
        verify(productRepo).save(product);
    }


    //**************************************************** Test DeleteAllProducts*****************************************************************//

    @Test
    @Order(7)
    public void testDeleteAllProducts() {
        // Set up
        List<Product> expectedProducts = new LinkedList<>();
        expectedProducts.add(new Product(1, "Product 1", "good one", 2, 200.2));
        expectedProducts.add(new Product(2, "Product 2", "shoes", 1, 400.50));
        productRepo.saveAll(expectedProducts);
        when(productRepo.count()).thenReturn(2L);
        // Execute the method
        String result = productService.deleteAllProducts();

        // Verify the result
        assertEquals("All products are deleted ", result);
        assertTrue(productRepo.findAll().isEmpty());
    }

    //**************************************************** Test SearchByProductName*****************************************************************//

    @Test
    @Order(8)
    public void testSearchByProductName() {
        // Set up
        List<Product> expectedProducts = new LinkedList<>();
        expectedProducts.add(new Product(1, "Product 1", "good one", 2, 200.2));
        expectedProducts.add(new Product(2, "Product 2", "shoes", 1, 400.50));
        expectedProducts.add(new Product(3, "Product 3", "SmartPhone", 1, 9500.50));
        productRepo.saveAll(expectedProducts);
        // Mock the repository to return the sample data
        when(productRepo.findByProductNameContainsAllIgnoreCase("o"))
                .thenReturn(Arrays.asList(expectedProducts.get(1), expectedProducts.get(2)));

        when(productRepo.findAllByOrderByProductNameAsc()).thenReturn(expectedProducts);

        // Call the method to test
        List<Product> result1 = productService.searchByProductName("o");
        List<Product> result2 = productService.searchByProductName(null);

        // Assertions
        assertEquals(2, result1.size());
        assertTrue(result1.contains(expectedProducts.get(1)));
        assertTrue(result1.contains(expectedProducts.get(2)));

        assertEquals(3, result2.size());
        assertEquals(expectedProducts, result2);
    }

    //**************************************************** Test UpdateProductQuantityInStock*****************************************************************//

    @Test
    @Order(9)
    public void testUpdateProductQuantityInStock() {
        // Arrange
        Integer productCode = 1;
        Integer originalQuantityInStock = 50;
        Integer quantity = 10;
        Product existingProduct = new Product();
        existingProduct.setProductCode(productCode);
        existingProduct.setQuantityInStock(originalQuantityInStock);
        when(productRepo.findById(productCode)).thenReturn(Optional.of(existingProduct));
        when(productRepo.save(existingProduct)).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.updateProductQuantityInStock(productCode, quantity);

        // Assert
        assertEquals(originalQuantityInStock - quantity, updatedProduct.getQuantityInStock());
        verify(productRepo).findById(productCode);
        verify(productRepo).save(existingProduct);
    }


    @Test
    @Order(10)
    public void testPartialUpdate() {
        // Arrange
        Integer productCode = 1;
        Map<String, Object> updates = new HashMap<>();
        updates.put("productName", "Updated Product Name");
        updates.put("price", 9.99);

        Product product = new Product();
        product.setProductName("Original Product Name");
        product.setPrice(19.99);

        when(productRepo.findById(productCode)).thenReturn(java.util.Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);
        // Act
        Product updatedProduct = productService.partialUpdate(productCode, updates);
        // Assert
        assertNotNull(updatedProduct);
        assertEquals("Updated Product Name", updatedProduct.getProductName());
        assertEquals(9.99, updatedProduct.getPrice());

        verify(productRepo, times(1)).findById(productCode);
        verify(productRepo, times(1)).save(product);
    }
}