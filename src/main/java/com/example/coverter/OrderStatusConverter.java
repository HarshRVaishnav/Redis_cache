package com.example.coverter;

import com.example.enums.OrderStatusVo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatusVo, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatusVo name) {
        return name.getValue().toString();
    }

    @Override
    public OrderStatusVo convertToEntityAttribute(String dbData) {
        return new OrderStatusVo(dbData);
    }
}