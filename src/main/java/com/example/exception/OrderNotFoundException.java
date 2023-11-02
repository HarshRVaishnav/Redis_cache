package com.example.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderNotFoundException extends RuntimeException {

    String message;
    int orderNumber;

    public OrderNotFoundException(String message) {
        super(message);
    }

 /*   public OrderNotFoundException(String message, int orderNumber) {
        this.message = message;
        this.orderNumber = orderNumber;
    }*/
}
