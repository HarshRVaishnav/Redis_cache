package com.example.dto;

import com.example.enums.OrderStatusVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    @Hidden
    private Integer orderNumber;

    @Hidden
    private LocalDate orderDate;

    @Hidden
    private LocalDate shippedDate;

    @Hidden
    private OrderStatusVo orderStatusVo;

    @Size(max = 500, message = "Comments cannot be more than 500 characters")
    @NotBlank(message = "To place Order comment must required")
    private String comments;

    @Hidden
    private Integer customerNumber;

    @JsonIgnore
    private CustomerDto customerDto;

    private List<OrderDetailsDto> orderDetails;

}