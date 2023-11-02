package com.example.service;

import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.enums.OrderStatusVo;
import com.example.repository.IOrderDetailsRepo;
import com.example.repository.IOrderRepo;
import com.example.serviceimpl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private IOrderRepo orderRepo;

    @Mock
    private IOrderDetailsRepo orderDetailsRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ICustomerService customerService;

    @Mock
    private IProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test               //TODO : check test case and modify & push code to new branch
    public void createOrder_ShouldCreateOrderAndSaveToRepository() {
        // Arrange
        Order order = new Order();
        order.setOrderNumber(1);
        order.setComments("Test Order");
        order.setOrderDate(LocalDate.now());
        order.setOrderStatusVo(new OrderStatusVo("CREATED"));
        order.setOrderDetails(new ArrayList<>());
        when(orderRepo.save(any(Order.class))).thenReturn(order);
        // Act
        Order createdOrder = orderService.createOrder(order);
        // Assert
        assertNotNull(createdOrder);
        assertEquals(Optional.of(1), createdOrder.getOrderNumber());
        //assertEquals(Optional.of(1), Optional.of(1));
        assertEquals("Test Order", createdOrder.getComments());
        assertEquals(LocalDate.now(), createdOrder.getOrderDate());
        assertEquals("CREATED", createdOrder.getOrderStatusVo());
        //assertEquals("CREATED", "CREATED");
        verify(orderRepo, times(1)).save(order);
    }


    @Test                   //TODO : check test case and modify & push code to new branch
    public void testCreateOrder() {
        // Arrange
        Order order = new Order();
        // Set up any required data for the order object
        // Mock productService.getlistbyproductCode() to return a list of products
        List<OrderDetails> orderDetails = new ArrayList<>();
        // Set up any required data for the orderDetails object
        order.setOrderDetails(orderDetails);
        when(productService.getlistbyproductCode(anyList())).thenReturn(new ArrayList<>());
        // Mock productService.updateProductQuantityInStock() to do nothing
        //doNothing().when(productService).updateProductQuantityInStock(anyInt(), anyInt());
        // Mock orderRepo.save() to return the saved order
        when(orderRepo.save(any(Order.class))).thenReturn(order);
        // Act
        Order createdOrder = orderService.createOrder(order);
        // Assert
        assertNotNull(createdOrder);
    }


    @Test
    public void testFindOrderById() {
        // Arrange
        int orderNumber = 1;
        Order order = new Order();
        when(orderRepo.findById(orderNumber)).thenReturn(Optional.of(order));
        // Act
        Order foundOrder = orderService.findOrderById(orderNumber);
        // Assert
        assertNotNull(foundOrder);
    }


    @Test
    public void testGetAllOrder() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        when(orderRepo.findAll()).thenReturn(orders);
        // Act
        List<Order> allOrders = orderService.getAllOrder();
        // Assert
        assertNotNull(allOrders);
    }


    @Test                   //TODO : check test case and modify & push code to new branch
    public void testDeleteOrder() {
        // Arrange
        int orderNumber = 1;
        Order order = new Order();
        order.setOrderDetails(new ArrayList<>());
        when(orderRepo.findById(orderNumber)).thenReturn(Optional.of(order));
        doNothing().when(productService).incrementDecrementProductQuantityInStock(anyInt(), anyString(), anyInt());
        doNothing().when(orderRepo).delete(any(Order.class));

        // Act
        String result = orderService.deleteOrder(orderNumber);

        // Assert
        assertNotNull(result);
    }
}
