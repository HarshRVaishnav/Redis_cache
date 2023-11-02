package com.example.controller;

import com.example.customresponse.CustomResponse;
import com.example.dto.CustomerDto;
import com.example.entity.Customer;
import com.example.exception.CustomerNotFoundException;
import com.example.service.ICustomerService;
import com.example.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private String code;

    private Object data;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private ModelMapper modelMapper;

    // Register Customer
    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody final CustomerDto customerDto) {
        try {
            Customer DtoToEntity = modelMapper.map(customerDto, Customer.class);
            Customer customerEntity = customerService.registerCustomer(DtoToEntity);
            CustomerDto customerToDto = modelMapper.map(customerEntity, CustomerDto.class);
            data = customerToDto;
            code = "CREATED";
        } catch (CustomerNotFoundException customerNotFoundException) {
            code = "DATA_NOT_CREATED";
            data = null;
        } catch (RuntimeException runtimeException) {
            code = "RUNTIME_EXCEPTION";
            data = null;
        } catch (Exception exception) {
            code = "EXCEPTION";
            data = null;
        } finally {
            return CustomResponse.response(code, data);
        }

    }

    // Get All Customer
    @GetMapping
    public ResponseEntity<?> getCustomerList() {
        try {
            List<Customer> customers = customerService.getCustomerList();
            data = customers;
            code = "SUCCESS";
        } catch (CustomerNotFoundException customerNotFoundException) {
            code = "DATA_NOT_FOUND";
            data = null;
        } catch (RuntimeException runtimeException) {
            code = "RUNTIME_EXCEPTION";
            data = null;
        } catch (Exception exception) {
            code = "EXCEPTION";
            data = null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    // Get customer By ID
    @GetMapping("{customerNumber}")
    public ResponseEntity<?> getCustomer(@PathVariable final Integer customerNumber) {
        try {

            Customer customer = customerService.getCustomerById(customerNumber);
            data = customer;

            code = "SUCCESS";
        } catch (CustomerNotFoundException customerNotFoundException) {
            data = null;
            code = "DATA_NOT_FOUND";
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

    // Get Customer By Last Name
    @GetMapping("/lastname/{customerLastName}")
    public ResponseEntity<?> searchBycustomerLastName(@PathVariable final String customerLastName) {
        try {
            Customer customers = customerService.findBycustomerLastName(customerLastName);
            data = customers;
            code = "SUCCESS";
        } catch (CustomerNotFoundException customerNotFoundException) {
            data = null;
            code = "DATA_NOT_FOUND";
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

    // Get Customer By First Name
    @GetMapping("/name/{customerFirstName}")
    public ResponseEntity<?> searchBycustomerFirstName(@PathVariable final String customerFirstName) {
        try {
            Customer customers = customerService.findBycustomerFirstName(customerFirstName);
            data = customers;
            code = "SUCCESS";
        } catch (CustomerNotFoundException customerNotFoundException) {
            data = null;
            code = "DATA_NOT_FOUND";
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

    // Delete Customer By ID
    @DeleteMapping("{customerNumber}")
    public ResponseEntity<?> deleteCustomer(@PathVariable final Integer customerNumber) {

        try {
            String customer = customerService.deleteCustomer(customerNumber);
            data = customer;
            code = "SUCCESS";
        } catch (CustomerNotFoundException customerNotFoundException) {
            data = null;
            code = "DATA_NOT_FOUND";
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

    // Edit or Update Customer Details
    @PutMapping("{customerNumber}")
    public ResponseEntity<?> updateCustomer(@PathVariable final Integer customerNumber, @RequestBody final CustomerDto customerDto) {
        try {
            Customer DtoToEntity = modelMapper.map(customerDto, Customer.class);
            Customer customerEntity = customerService.updateCustomerDetail(customerNumber, DtoToEntity);
            CustomerDto customerToDto = modelMapper.map(customerEntity, CustomerDto.class);
            data = customerToDto;
            code = "CREATED";
        } catch (CustomerNotFoundException customerNotFoundException) {
            data = null;
            code = "DATA_NOT_FOUND";
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