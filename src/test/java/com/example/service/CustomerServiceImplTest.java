package com.example.service;

import com.example.entity.Customer;
import com.example.repository.ICustomerRepo;
import com.example.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private ICustomerRepo customerRepository;

//        @Mock
//        private CacheManager cacheManager;


    @Test
    @Order(1)
    public void test_getAllCustomer() {
        List<Customer> customerlist = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerFirstName("satish");
        customer.setCustomerLastName("gaikwad");
        customer.setAddressLine1("Kolpewadi");
        customer.setAddressLine2("Kolpewadi");
        customer.setPhone("902270086");
        customer.setPostalCode(423602);
        customer.setCity("ahmednagar");
        customer.setState("maharashtra");
        customer.setCountry("india");
        customerlist.add(customer);
        when(customerRepository.findAll()).thenReturn(customerlist);
        assertEquals(1, customerService.getCustomerList().size());
    }

    @Test
    @Order(2)
    public void test_getById() {
        Customer customer = new Customer();
        customer.setCustomerNumber(1);
        customer.setCustomerFirstName("satish");
        customer.setCustomerLastName("gaikwad");
        customer.setAddressLine1("Kolpewadi");
        customer.setAddressLine2("Kolpewadi");
        customer.setPhone("902270086");
        customer.setPostalCode(423602);
        customer.setCity("ahmednagar");
        customer.setState("maharashtra");
        customer.setCountry("india");
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer actualCustomer = customerService.getCustomerById(1);
        assertEquals(1, actualCustomer.getCustomerNumber());
    }


    @Test
    public void testDeleteCustomer() {
        // Arrange
        Integer customerNumber = 123;
        Customer customer = new Customer();
        customer.setCustomerNumber(customerNumber);
        when(customerRepository.findById(customerNumber)).thenReturn(Optional.of(customer));

        // Act
        String result = customerService.deleteCustomer(customerNumber);

        // Assert
        verify(customerRepository, times(1)).findById(customerNumber);
        verify(customerRepository, times(1)).delete(customer);
        // verify(cacheManager.getCache("customer")).clear();
        assertEquals("Customer Deleted Successfully with customerNumber: " + customerNumber, result);
    }


    @Test
    @Order(4)
    public void test__UpdateCustomerDetail() {
        // Given
        Integer customerNumber = 1;
        Customer customer = new Customer();

        customer.setCustomerFirstName("John");
        customer.setCustomerLastName("Doe");
        customer.setAddressLine1("123 Main St");
        customer.setAddressLine2("");
        customer.setCity("Anytown");
        customer.setCountry("USA");
        customer.setPostalCode(12345);
        customer.setState("CA");
        customer.setPhone("555-1234");
        Optional<Customer> optionalCustomer = Optional.of(customer);
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerFirstName("Jahn");
        updatedCustomer.setCustomerLastName("Doae");
        updatedCustomer.setAddressLine1("123 Maina St");
        updatedCustomer.setAddressLine2("");
        updatedCustomer.setCity("Anytoawn");
        updatedCustomer.setCountry("USaA");
        updatedCustomer.setPostalCode(123485);
        updatedCustomer.setState("CaA");
        updatedCustomer.setPhone("555-1234");

        when(customerRepository.findById(customerNumber)).thenReturn(optionalCustomer);
        when(customerRepository.save(customer)).thenReturn(updatedCustomer);

        // When
        Customer result = customerService.updateCustomerDetail(customerNumber, customer);

        // Then
        assertEquals(updatedCustomer, result);
        verify(customerRepository).save(customer);
    }


    @Test
    @Order(5)
    public void testRegisterCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setCustomerFirstName("Jahn");
        customer.setCustomerLastName("Doae");
        customer.setAddressLine1("123 Maina St");
        customer.setAddressLine2("");
        customer.setCity("Anytoawn");
        customer.setCountry("USaA");
        customer.setPostalCode(123485);
        customer.setState("CaA");
        customer.setPhone("555-1234");
        when(customerRepository.save(customer)).thenReturn(customer);
        //  ICustomerService customerService = new CustomerService(iCustomerRepo);

        // When
        Customer result = customerService.registerCustomer(customer);

        // Then
        assertEquals(customer, result);
        verify(customerRepository).save(customer);
    }


}
