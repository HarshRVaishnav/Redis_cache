package com.example.controller;


import com.example.customresponse.CustomResponse;
import com.example.dto.OrderDto;
import com.example.entity.Order;
import com.example.enums.OrderStatusVo;
import com.example.exception.OrderNotFoundException;
import com.example.service.IOrderService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private String code;

    private Object data;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<?> getAllOrder() {
        try {
            List<Order> list = iOrderService.getAllOrder();
            data = list;
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


    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody final OrderDto orderDto) {
        try {
            Order DtoToEntity = modelMapper.map(orderDto, Order.class);
            Order orderEntity = iOrderService.createOrder(DtoToEntity);
            OrderDto orderToDto = modelMapper.map(orderEntity, OrderDto.class);
            data = orderToDto;
            code = "CREATED";
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


    @GetMapping("{orderNumber}")
    public ResponseEntity<?> showOrderById(@PathVariable final Integer orderNumber) {
        try {
            Order orderFound = iOrderService.findOrderById(orderNumber);
            OrderDto orderToDto = modelMapper.map(orderFound, OrderDto.class);
            data = orderToDto;
            code = "SUCCESS";
        } catch (OrderNotFoundException orderNotFoundException) {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException r) {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception e) {
            data = null;
            code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    @DeleteMapping("{orderNumber}")
    public ResponseEntity<?> deleteOrder(@PathVariable final Integer orderNumber) {
        try {
            String deletedOrder = iOrderService.deleteOrder(orderNumber);
            data = deletedOrder;
            code = "SUCCESS";
        } catch (OrderNotFoundException e) {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException runtimeException) {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception e) {
            data = null;
            code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    //@Hidden
    @PatchMapping("/updateShippingDate/{orderNumber}")
    public ResponseEntity<?> updateShippingDate(@Valid @PathVariable final Integer orderNumber,
                                                @RequestBody @Valid final OrderDto orderDto) {
        try {
            Order DtoToEntity = modelMapper.map(orderDto, Order.class);
            Order updatedDate = iOrderService.updateShippingDate(orderNumber, DtoToEntity);
            OrderDto orderToDto = modelMapper.map(updatedDate, OrderDto.class);
            data = orderToDto;
            code = "SUCCESS";
        } catch (OrderNotFoundException e) {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException runtimeException) {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception r) {
            data = null;
            code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }


   // @Hidden
    @PatchMapping("/updateStatus/{orderNumber}")
    public ResponseEntity<?> updateStatus(@PathVariable @Valid final Integer orderNumber,
                                          @RequestBody @Valid final OrderStatusVo orderStatusVo) {
        try {
            //Order DtoToEntity = modelMapper.map(orderDto, Order.class);
            //OrderStatusVo updatedDate = iOrderService.updateStatus(orderNumber, DtoToEntity);
            //OrderDto orderToDto = modelMapper.map(updatedDate, OrderDto.class);
            OrderStatusVo updatedStatus = iOrderService.updateStatus(orderNumber, orderStatusVo);
            data = updatedStatus;
            code = "SUCCESS";
        } catch (OrderNotFoundException orderNotFoundException) {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException runtimeException) {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data = null;
            code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }
}