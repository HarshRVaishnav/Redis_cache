package com.example.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;


@Data
public class ProductDto implements Serializable {

    @Hidden
    private Integer productCode;

    @Size(min = 1, max = 50, message = "Customer First Name Must be between 0 to 55")
    @NotBlank(message = "Product Name cannot be blank")
    @Pattern(regexp = "^[A-Z a-z]*$", message = "Invalid Input")
    private String productName;

    @Size(min = 0, max = 100, message = "Product Description Name Must be between 0 to 55")
    @NotBlank(message = "Product Description cannot be blank")
    private String productDescription;

    @Min(value = 1, message = "Quantity in stock must be at least 0")
    private Integer quantityInStock;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "100.00", message = "Price must be at least 100.00")
    private Double price;

}