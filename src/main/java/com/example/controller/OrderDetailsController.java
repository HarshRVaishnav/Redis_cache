package com.example.controller;

import com.example.customresponse.CustomResponse;
import com.example.dto.OrderDto;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.exception.OrderNotFoundException;
import com.example.repository.IOrderDetailsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/orderDetails")
public class OrderDetailsController {

    private String code;

    private Object data;

    @Autowired
    private IOrderDetailsRepo orderDetailsRepo;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/list")
    public ResponseEntity<?> getOrder() {
        try {
            Order orderEntity = (Order) orderDetailsRepo.findAll();
            data = modelMapper.map(orderEntity, OrderDto.class);
            code = "SUCCESS";
        } catch (OrderNotFoundException orderNotFoundException) {
            code = "DATA_NOT_CREATED";
            data = null;
        } catch (RuntimeException order) {
            code = "RUNTIME_EXCEPTION";
            data = null;
        } catch (Exception e) {
            code = "EXCEPTION";
            data = null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable final int orderId) {
        try {
            Optional<OrderDetails> orderEntity = orderDetailsRepo.findById(orderId);
            data = modelMapper.map(orderEntity, OrderDto.class);
            code = "SUCCESS";
        } catch (OrderNotFoundException orderNotFoundException) {
            code = "DATA_NOT_CREATED";
            data = null;
        } catch (RuntimeException order) {
            code = "RUNTIME_EXCEPTION";
            data = null;
        } catch (Exception e) {
            code = "EXCEPTION";
            data = null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }
}
