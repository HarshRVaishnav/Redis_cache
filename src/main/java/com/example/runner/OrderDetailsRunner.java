package com.example.runner;

import com.example.entity.OrderDetails;
import com.example.repository.IOrderDetailsRepo;
import com.example.repository.IOrderRepo;
import com.example.repository.IProductRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Component
@org.springframework.core.annotation.Order(20)
public class OrderDetailsRunner implements CommandLineRunner {

    @Autowired
    private IOrderDetailsRepo orderDetailsRepo;


    private static final int LOOP_CUNT = 5;

    @Override
    public void run(String... args) {
        if (orderDetailsRepo.count() == 0) {
            List<OrderDetails> list = new LinkedList<>();
            Faker faker = new Faker(new Locale("en-IND"));
            for (int i = 1; i < LOOP_CUNT; i++) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderNumber(i);
                orderDetails.setQuantityOrdered(faker.number().numberBetween(1, 2));
                orderDetails.setProductCode(i);
                orderDetails.setPriceEach(faker.number().randomDouble(2, 100, 1000));
                list.add(orderDetails);
            }
            orderDetailsRepo.saveAll(list);
        }
    }
}
