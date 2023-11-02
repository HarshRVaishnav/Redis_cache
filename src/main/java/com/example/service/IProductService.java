
package com.example.service;

import com.example.entity.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {

    public Product createProduct(Product product);

    public List<Product> createProductList(List<Product> product);

    public Product updateProduct(Integer productCode, Product product);

    public Product getProductById(Integer productCode);

    public List<Product> getAllProducts();

    public String deleteProductById(Integer productCode);

    public String deleteAllProducts();

    public Product partialUpdate(Integer productCode, Map<String, Object> updates);

    public List<Product> getAllSortedByProduct();

    public List<Product> searchByProductName(String name);

    public Product incrementDecrementProductPrice(Integer productCode, String value, Double amount);

    public Product incrementDecrementProductQuantityInStock(Integer productCode, String value, Integer quantity);

    public Product updateProductQuantityInStock(Integer productCode, Integer quantity);

    public List<Product> getlistbyproductCode(List<Integer> productCode);

    public void createProductsList();
}
