package com.example.serviceimpl;

import com.example.entity.Product;
import com.example.exception.ProductNotFoundException;
import com.example.repository.IProductRepo;
import com.example.service.IProductService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepo productRepo;

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }


    @Override
    public List<Product> createProductList(List<Product> product) {
        return productRepo.saveAll(product);
    }


    @Override
    @CachePut(value = "product", key = "#productCode")
    public Product updateProduct(Integer productCode, Product product) {
        Product existingProduct = getProductById(productCode);
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductDescription(product.getProductDescription());
            existingProduct.setQuantityInStock(product.getQuantityInStock());
            existingProduct.setPrice(product.getPrice());
            return productRepo.save(existingProduct);
        } else {
            throw new ProductNotFoundException();
        }
    }


    @Override
    @CachePut(value = "product", key = "#productCode")
    public Product partialUpdate(Integer productCode, Map<String, Object> updates) {
        Product product = productRepo.findById(productCode).orElseThrow(() -> new ProductNotFoundException(String.valueOf(productCode)));
        ReflectionUtils.doWithFields(Product.class, field -> {
            String fieldName = field.getName();
            if (updates.containsKey(fieldName)) {
                field.setAccessible(true);
                Object newValue = updates.get(fieldName);
                field.set(product, newValue);
            }
        });
        return productRepo.save(product);
    }


    @Override
    @CacheEvict(value = "product", allEntries = true)
    public String deleteProductById(Integer productCode) {
        Product product = productRepo.findById(productCode).orElseThrow(() -> new ProductNotFoundException(String.valueOf(productCode)));
        productRepo.delete(product);
        return "One product deleted with productCode: " + productCode;
    }


    @Override
    public void createProductsList() {
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < 10000; i++) {
            Faker faker = new Faker(new Locale("en-IND"));
            Product product = new Product();
            product.setProductName(faker.commerce().productName());
            product.setProductDescription(faker.lorem().sentence());
            product.setQuantityInStock(faker.number().numberBetween(1, 100));
            product.setPrice(faker.number().randomDouble(2, 102, 10000));
            products.add(product);
        }
        productRepo.saveAll(products);
    }


    @Override
    @CacheEvict(value = "product", allEntries = true)
    public String deleteAllProducts() {
        if (productRepo.count() != 0) {
            productRepo.deleteAllInBatch();
            return "All products are deleted ";
        } else {
            throw new ProductNotFoundException();
        }
    }


    @Override
    @Cacheable(value = "product", key = "#productCode")
    public Product getProductById(Integer productCode) {
        Product product = productRepo.findById(productCode).orElseThrow(() -> new ProductNotFoundException(String.valueOf(productCode)));
        log.info("-- from database getProductById()-- {}", product);
        return product;
    }


    @Override
    public List<Product> getAllProducts() {
        if (productRepo.count() != 0) {
            List<Product> productList = productRepo.findAll();
            log.info("-- from database getAllProducts()-- {}", productList);
            return productList;
        } else {
            throw new ProductNotFoundException();
        }
    }


    @Override
    public List<Product> getAllSortedByProduct() {
        if (productRepo.count() != 0) {
            return productRepo.findAllByOrderByProductNameAsc();
        } else {
            throw new ProductNotFoundException();
        }
    }


    @Override
    public List<Product> searchByProductName(String name) {
        if (name != null && (name.trim().length() > 0)) {
            return productRepo.findByProductNameContainsAllIgnoreCase(name);
        } else {
            return productRepo.findAllByOrderByProductNameAsc();       //return getAllSortedByProduct();
        }
    }


    //update quantity in stock for order creation
    @Transactional
    @Override
    public Product updateProductQuantityInStock(Integer productCode, Integer quantity) {
        Product existingProduct = getProductById(productCode);
        Integer originalQuantityInStock = existingProduct.getQuantityInStock();
        try {
            Integer updateProductQuantity = existingProduct.getQuantityInStock() - quantity;
            existingProduct.setQuantityInStock(updateProductQuantity);
            return productRepo.save(existingProduct);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }


    //fetch list of product based on list of productCode
    @Override
    public List<Product> getlistbyproductCode(List<Integer> productCode) {
        return productRepo.findByProductCodeIn(productCode);
    }


    @Override
    public Product incrementDecrementProductQuantityInStock(Integer productCode, String value, Integer quantity) {
        Product existingProduct = getProductById(productCode);
        Integer originalQuantityInStock = existingProduct.getQuantityInStock();
        if ((existingProduct != null) && (value.equalsIgnoreCase("increment")) && (1 <= quantity)) {
            Integer increaseProductPriceQuantity = existingProduct.getQuantityInStock() + quantity;
            //Integer newQuantity =  Quantity;    //existingProduct.setQuantityInStock(newQuantity);    //to set new Quantity
            existingProduct.setQuantityInStock(increaseProductPriceQuantity);
            return productRepo.save(existingProduct);
        }
        if ((existingProduct != null) && (value.equalsIgnoreCase("decrement")) && (originalQuantityInStock >= quantity)) {
            Integer decreaseProductPriceQuantity = existingProduct.getQuantityInStock() - quantity;
            //Integer newQuantity =  Quantity;    //existingProduct.setQuantityInStock(newQuantity);    //to set new Quantity
            existingProduct.setQuantityInStock(decreaseProductPriceQuantity);
            return productRepo.save(existingProduct);
        } else {
            throw new ProductNotFoundException("for increment quantity cannot be less than zero & for decrement quantity " + "cannot be greater than original quantity");
        }
    }


    @Override
    public Product incrementDecrementProductPrice(Integer productCode, String value, Double amount) {
        Product existingProduct = getProductById(productCode);
        Double originalPrice = existingProduct.getPrice();
        if ((existingProduct != null) && (value.equalsIgnoreCase("increment")) && (1 <= amount)) {
            Double increaseProductPriceAmount = existingProduct.getPrice() + amount;
            //Double newPrice =  amount;    //existingProduct.setPrice(newPrice);    //to set new price
            existingProduct.setPrice(increaseProductPriceAmount);
            return productRepo.save(existingProduct);
        }
        if ((existingProduct != null) && (value.equalsIgnoreCase("decrement")) && (originalPrice >= amount)) {
            Double decreaseProductPriceAmount = existingProduct.getPrice() - amount;
            //Double newPrice =  amount;    //existingProduct.setPrice(newPrice);    //to set new price
            existingProduct.setPrice(decreaseProductPriceAmount);
            return productRepo.save(existingProduct);
        } else {
            throw new ProductNotFoundException("for increment amount cannot be less than zero & for decrement amount cannot be " + "greater than original price");
        }
    }
}