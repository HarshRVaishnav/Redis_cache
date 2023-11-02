package com.example.serviceimpl;

import com.example.entity.Customer;
import com.example.exception.CustomerNotFoundException;
import com.example.repository.ICustomerRepo;
import com.example.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepo customerRepo;

    @Override
    public List<Customer> getCustomerList() {
        return customerRepo.findAll();
    }

    @Override
    @CachePut(value = "customer",key="#customerNumber")
    public Customer updateCustomerDetail(Integer customerNumber, Customer c) {
        Optional<Customer> customer1 = customerRepo.findById(customerNumber);
        if (customer1.isPresent()) {
            Customer customer = customer1.get();
            customer.setCustomerFirstName(c.getCustomerFirstName());
            customer.setCustomerLastName(c.getCustomerLastName());
            customer.setAddressLine1(c.getAddressLine1());
            customer.setAddressLine2(c.getAddressLine2());
            customer.setCity(c.getCity());
            customer.setCountry(c.getCountry());
            customer.setPostalCode(c.getPostalCode());
            customer.setState(c.getState());
            customer.setPhone(c.getPhone());
            return customerRepo.save(customer);
        } else {
           throw new CustomerNotFoundException();
        }
    }

    @Override
    @Cacheable(value = "customer",key="#customerNumber")
    public Customer getCustomerById(Integer customerNumber) {
        return customerRepo.findById(customerNumber).orElseThrow(() -> new CustomerNotFoundException());
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    @CacheEvict(value = "customer",allEntries = true)
    public String deleteCustomer(Integer customerNumber) {
        Customer customer = customerRepo.findById(customerNumber).orElseThrow(() -> new CustomerNotFoundException());
        customerRepo.delete(customer);
        return "Customer Deleted Successfully with customerNumber: "+customerNumber;
    }

    @Override
    public Customer findBycustomerFirstName(String customerFirstName) {
        return customerRepo.findBycustomerFirstName(customerFirstName);
    }

    @Override
    public Customer findBycustomerLastName(String customerLastName) {
        return customerRepo.findBycustomerLastName(customerLastName);
    }
}
