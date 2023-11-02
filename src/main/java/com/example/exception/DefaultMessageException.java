package com.example.exception;

public class DefaultMessageException extends  RuntimeException{

    public DefaultMessageException(){
        super();
    }
    public DefaultMessageException(String message){
        super(message);
    }
}
