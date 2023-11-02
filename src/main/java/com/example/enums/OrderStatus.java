package com.example.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    CREATED, ORDERED, CANCELED, PENDING, SHIPPED, DELIVERED;

}
