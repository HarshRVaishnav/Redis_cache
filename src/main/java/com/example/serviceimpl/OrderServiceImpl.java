package com.example.serviceimpl;

import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.enums.OrderStatusVo;
import com.example.exception.OrderNotFoundException;
import com.example.exception.ProductNotFoundException;
import com.example.repository.IOrderDetailsRepo;
import com.example.repository.IOrderRepo;
import com.example.service.ICustomerService;
import com.example.service.IOrderService;
import com.example.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepo orderRepo;

    @Autowired
    private IOrderDetailsRepo orderDetailsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProductService productService;


    @Override
    public Order createOrder(Order order) {
        order.setComments(order.getComments());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatusVo(new OrderStatusVo("CREATED"));
        orderRepo.save(order);
        List<OrderDetails> orderDetailsDtoList = order.getOrderDetails();
        List<Integer> pid = orderDetailsDtoList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.getlistbyproductCode(pid);

        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        //Product product1 = productMap.get(orderDetailsDto.getProductCode());

        for (OrderDetails orderDetailsDto : orderDetailsDtoList) {
            Product product = productMap.get(orderDetailsDto.getProductCode());
            if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                throw new ProductNotFoundException("Product is out of stock");
            }
            productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDto.getQuantityOrdered());
            OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            orderDetails.setProductCode(product.getProductCode());
            orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
            orderDetails.setPriceEach(product.getPrice());
            //orderDetails.setProduct(product);
            //orderDetails.setOrder(order);
            orderDetails.setOrderNumber(order.getOrderNumber());
            orderDetailsList.add(orderDetails);
        }
        orderDetailsRepo.saveAll(orderDetailsList);
        //order.setOrderDetails(orderDetailsList);
        //orderRepo.save(order);
        return order;
    }


    @Override
    public Order createOrder2(Order order) {
        order.setComments(order.getComments());
        //order.set
        orderRepo.save(order);
        List<OrderDetails> orderDetailsList1 = new ArrayList<>();
        List<OrderDetails> orderDetailsDTOList = order.getOrderDetails();
        List<Integer> pid = orderDetailsDTOList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
        List<Product> products = productService.getlistbyproductCode(pid);
        log.info("product entity : " + products.toString());
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));
        for (int i = 0; i < orderDetailsDTOList.size(); i++) {
            OrderDetails orderDetailsDTO = orderDetailsDTOList.get(i);
            Product product = productMap.get(orderDetailsDTO.getProductCode());
            if ((product.getQuantityInStock() >= orderDetailsDTO.getQuantityOrdered())) {
                OrderDetails orderDetails = modelMapper.map(orderDetailsDTOList, OrderDetails.class);
                productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDTO.getQuantityOrdered());
                orderDetails.setOrderNumber(order.getOrderNumber());
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetails.setProduct(product);
                orderDetailsList1.add(orderDetails);      //orderDetailsRepo.save(orderDetails);
                log.info("orderDetails entity : " + orderDetails.toString());
            } else {
                throw new ProductNotFoundException("Product is out of stock");
            }
        }
        orderDetailsRepo.saveAll(orderDetailsList1);
        log.info("order entity : " + order.toString());
        return order;
    }


    @Override
    @Cacheable(value = "order",key="#orderNumber")
    public Order findOrderById(Integer orderNumber) {
        return orderRepo.findById(orderNumber).orElseThrow(() -> new OrderNotFoundException());
    }


    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }


    @Override
    @CacheEvict(value = "order",allEntries = true)
    public String deleteOrder(Integer orderNumber) {
        Optional<Order> optionalOrder = orderRepo.findById(orderNumber);
        // Order orders = null;
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            // Update the quantity of each product that was ordered in this order
            for (OrderDetails orderDetails : order.getOrderDetails()) {
                int productCode = orderDetails.getProductCode();
                int quantityOrdered = orderDetails.getQuantityOrdered();
                productService.incrementDecrementProductQuantityInStock(productCode, "increment", quantityOrdered);
            }
            orderRepo.delete(order);
            return "Order with order number " + orderNumber + " has been deleted";
        } else {
            throw new OrderNotFoundException();
        }
    }



    @Override
    public OrderStatusVo updateStatus(Integer orderNumber, OrderStatusVo orderStatusVo) {
        Optional<Order> foundOrder = orderRepo.findById(orderNumber);
        if (foundOrder.isPresent()) {
            Order order = new Order();
            order.setOrderStatusVo(orderStatusVo);
            orderRepo.save(order);
            return orderStatusVo;
        } else {
            throw new OrderNotFoundException();
        }
    }


    @Override
    public Order updateShippingDate(Integer orderNumber, Order order) {
        Optional<Order> foundOrder = orderRepo.findById(orderNumber);
        if (foundOrder.isPresent()) {
            order.setShippedDate(order.getShippedDate());
            return orderRepo.save(order);
        } else {
            throw new OrderNotFoundException();
        }
    }



    // @Override
    public Order updateOrder(Order order) {
        Order orders = orderRepo.findById(order.getOrderNumber()).get();
        try {
            orders.setOrderNumber(order.getOrderNumber());
            orders.setComments(order.getComments());
            orders.setOrderStatusVo(new OrderStatusVo("CREATED"));
            orderRepo.save(order);
            List<OrderDetails> orderDetailsDtoList = order.getOrderDetails();
            List<Integer> pid = orderDetailsDtoList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());
            List<Product> products = productService.getlistbyproductCode(pid);
            Map<Integer, Product> productMap = products.stream()
                    .collect(Collectors.toMap(Product::getProductCode, product -> product));
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            for (OrderDetails orderDetailsDto : orderDetailsDtoList) {
                Product product = productMap.get(orderDetailsDto.getProductCode());
                if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered()) {
                    throw new ProductNotFoundException("Product is out of stock");
                }
                productService.updateProductQuantityInStock(product.getProductCode(), orderDetailsDto.getQuantityOrdered());
                OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
                orderDetails.setProductCode(product.getProductCode());
                orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
                orderDetails.setPriceEach(product.getPrice());
                orderDetails.setOrderNumber(order.getOrderNumber());
                orderDetailsList.add(orderDetails);
            }
            orderDetailsRepo.saveAll(orderDetailsList);
            return orders;
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }
}
