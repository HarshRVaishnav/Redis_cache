package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderDetailsDto implements Serializable {

    @Hidden
    private Integer orderId;

    @Hidden
    private Integer orderNumber;

    @NotNull(message = "Product code cannot be null")
    private Integer productCode;

    @Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    @Hidden
    private Double priceEach;

    @Hidden
    @JsonIgnore
    private ProductDto productDto;

}
