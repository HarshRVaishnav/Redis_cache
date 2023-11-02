package com.example.runner;

import com.example.entity.Product;
import com.example.repository.IProductRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Component
@Order(5)
public class ProductRunner implements CommandLineRunner {

    @Autowired
    private IProductRepo productRepo;

    private static final int LOOP_CUNT=50;

    @Override
    public void run(String... args) {
        if (productRepo.count() == 0) {
            List<Product> list=new LinkedList<>();
            Faker faker = new Faker(new Locale("en-IND"));
            for (int i = 1; i < LOOP_CUNT; i++) {
                Product product = new Product();
                product.setProductName(faker.commerce().productName());
                product.setProductDescription(faker.lorem().sentence());
                product.setQuantityInStock(faker.number().numberBetween(1, 100));
                product.setPrice(faker.number().randomDouble(2, 102, 10000));
                list.add(product);
            }
            productRepo.saveAll(list);
        }
    }
}
