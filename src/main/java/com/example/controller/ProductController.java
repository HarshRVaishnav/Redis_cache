package com.example.controller;

import com.example.customresponse.CustomResponse;
import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.exception.ProductNotFoundException;
import com.example.serviceimpl.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private String code;

    private Object data;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ModelMapper modelMapper;

    //add one product to table
    @PostMapping
    public ResponseEntity<Object> addOneProduct(@Valid @RequestBody final ProductDto productDto) {
        try {
            Product DtoToEntity = modelMapper.map(productDto, Product.class);
            Product productEntity = productService.createProduct(DtoToEntity);
            ProductDto productToDto = modelMapper.map(productEntity, ProductDto.class);
            //Product product1 = productService.createProduct(product);
            data = productToDto;
            code = "CREATED";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null; code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null; code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    //add list of product to table
    @PostMapping(value = "/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createMultipleProduct(@Valid @RequestBody final List<ProductDto> productDto) {
        try {
            Product DtoToEntity = modelMapper.map(productDto, Product.class);
            //Product productEntity = (Product) productService.createProductList((List<Product>) DtoToEntity);
            List<Product> productEntity = productService.createProductList((List<Product>) DtoToEntity);
            ProductDto productToDto = modelMapper.map(productEntity, ProductDto.class);
            //List<Product> productList = productService.createProductList(product);
            data = productToDto;
            code = "CREATED";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null;  code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null; code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    //modify one product by id
    @PutMapping(value = "{productCode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateOneProduct(@PathVariable final Integer productCode,@Valid @RequestBody final ProductDto productDto) {
        try {
            Product DtoToEntity = modelMapper.map(productDto, Product.class);
            Product productEntity = productService.updateProduct(productCode,DtoToEntity);
            ProductDto productToDto = modelMapper.map(productEntity, ProductDto.class);
           // Product product1 = productService.updateProduct(productCode, product);
            data = productToDto;
            code = "CREATED";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null; code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null;  code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    //partial update by id
    @PatchMapping(value = "{productCode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> partialUpdate(@PathVariable final Integer productCode, @RequestBody final Map<String, Object> update) {
        try {
            Product product = productService.partialUpdate(productCode, update);
            data = product;
            code = "CREATED";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null; code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null; code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    //delete product by using id
    @DeleteMapping(value = "{productCode}")
    public ResponseEntity<Object> deleteOneProduct( @PathVariable final Integer productCode) {
        try {
            String product = productService.deleteProductById(productCode);
            data = product;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null; code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null; code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    //delete all products
    @DeleteMapping
    public ResponseEntity<Object> deleteAllProduct() {
        try {
            String products = productService.deleteAllProducts();
            data = products;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null; code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null; code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }

    //fetch product by id
    @GetMapping(value = "{productCode}")
    public ResponseEntity<Object> getOneProduct(@PathVariable final Integer productCode) {
        try {
            Product oneProduct = productService.getProductById(productCode);
            data = oneProduct;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null; code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null;code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null;code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    //fetch all products
    @GetMapping
    public ResponseEntity<Object> getAllProduct() {
        try {
            List<ProductDto> products = productService.getAllProducts().stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
            data = products;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            code = "DATA_NOT_FOUND"; data =null;
        } catch (RuntimeException runtimeException) {
            code = "RUNTIME_EXCEPTION"; data =null;
        } catch (Exception exception) {
            code = "EXCEPTION"; data =null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    //sort products according to ascending order
    @GetMapping("sort")
    public ResponseEntity<Object> getAllProductSorted() {
        try {
            List<ProductDto> products = productService.getAllSortedByProduct().stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
            data = products;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            code = "DATA_NOT_FOUND"; data =null;
        } catch (RuntimeException runtimeException) {
            code = "RUNTIME_EXCEPTION"; data =null;
        } catch (Exception exception) {
            code = "EXCEPTION"; data =null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    //search product by productName
    @GetMapping("search/{productName}")
    public ResponseEntity<Object> searchByproduct(@PathVariable final String productName) {
        try {
            List<Product> products = productService.searchByProductName(productName);
            data = products;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            code = "DATA_NOT_FOUND"; data =null;
        } catch (RuntimeException runtimeException) {
            code = "RUNTIME_EXCEPTION"; data =null;
        } catch (Exception exception) {
            code = "EXCEPTION"; data =null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    //update price of product (increase/decrease)
    @PostMapping("{productCode}/price/{value}/{amount}")
    public ResponseEntity<Object> updatePrice(@PathVariable final Integer productCode, @PathVariable final String value, @PathVariable final Double amount) {
        try {
            Product products = productService.incrementDecrementProductPrice(productCode, value, amount);
            data = products;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            code = "DATA_NOT_FOUND"; data =null;
        } catch (RuntimeException runtimeException) {
            code = "RUNTIME_EXCEPTION"; data =null;
        } catch (Exception exception) {
            code = "EXCEPTION"; data =null;
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    //update quantity of product (increase/decrease)
    @PostMapping("{productCode}/quantity/{value}/{quantity}")
    public ResponseEntity<Object> updateQuantityInStock(@PathVariable final Integer productCode, @PathVariable final String value, @PathVariable final Integer quantity) {
        try {
            Product products = productService.incrementDecrementProductQuantityInStock(productCode, value, quantity);
            data = products;
            code = "SUCCESS";
        } catch (ProductNotFoundException productNotFoundException) {
            data =null;  code = "DATA_NOT_FOUND";
        } catch (RuntimeException runtimeException) {
            data =null; code = "RUNTIME_EXCEPTION";
        } catch (Exception exception) {
            data =null; code = "EXCEPTION";
        } finally {
            return CustomResponse.response(code, data);
        }
    }


    @PostMapping("/createlist")
    public String createProductList() {
        productService.createProductsList();
        return "created";
    }
}


