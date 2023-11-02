package com.example.enums;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Schema(implementation = OrderStatus.class, description = "OrderStatus")
public class OrderStatusVo {
    OrderStatus value;

    public OrderStatusVo(String dbData) {
        value = OrderStatus.valueOf(dbData);
    }

}
