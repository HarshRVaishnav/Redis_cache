package com.example.service;


import com.example.entity.Order;
import com.example.enums.OrderStatusVo;

import java.util.List;

public interface IOrderService {

    public Order createOrder( Order order);

    public Order createOrder2(Order order);

    public List<Order> getAllOrder();

    public Order findOrderById(Integer orderNumber);

    public String deleteOrder(Integer orderNumber);

    public Order updateOrder(Order order);

    //public OrderStatusVo updateStatus(Integer orderNumber, Order order);
    public OrderStatusVo updateStatus(Integer orderNumber, OrderStatusVo order);

    public Order updateShippingDate(Integer orderNumber, Order order);

}
