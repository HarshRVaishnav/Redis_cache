package com.example.runner;

import com.example.entity.Order;
import com.example.enums.OrderStatusVo;
import com.example.repository.ICustomerRepo;
import com.example.repository.IOrderRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@org.springframework.core.annotation.Order(10)
@Component
public class OrderRunner implements CommandLineRunner {

    @Autowired
    private ICustomerRepo customerRepo;

    @Autowired
    private IOrderRepo orderRepo;

    private static final int LOOP_CUNT=5;

    @Override
    public void run(String... args){
        if (orderRepo.count() == 0) {
            List<Order> list = new LinkedList<>();
            Faker faker = new Faker(new Locale("en-IND"));
            for (int i = 1; i < LOOP_CUNT; i++) {
                Order order = new Order();
                order.setOrderDate(LocalDate.now());
                order.setComments(faker.lorem().sentence());
                order.setOrderStatusVo(new OrderStatusVo("DELIVERED"));
                order.setCustomerNumber(i);
                order.setCustomer(customerRepo.findById(i).get());
                order.setShippedDate(LocalDate.now().plusDays(2));
                order.setOrderDetails(List.of());
                list.add(order);
            }
            orderRepo.saveAll(list);
        }
    }
}
