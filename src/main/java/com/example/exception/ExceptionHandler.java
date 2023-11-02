package com.example.exception;

import com.example.customresponse.CustomResponse;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.modelmapper.MappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.CommunicationException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<?> handleNoHandlerFoundException1(NoHandlerFoundException noHandlerFoundException) {
        String error = "No handler found for " + noHandlerFoundException.getHttpMethod() + " " + noHandlerFoundException.getRequestURL();
        log.info("handleNoHandlerFoundException : {} ", noHandlerFoundException.getMessage() + error);
        return CustomResponse.response("SERVICE_UNAVAILABLE");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({CommunicationException.class, JDBCConnectionException.class, ConnectException.class, SocketTimeoutException.class})
    protected ResponseEntity<?> handleNoHandlerFoundException(Exception exception) {
        log.info("handleNoHandlerFoundException : {} ", exception.getMessage());
        return CustomResponse.response("SERVER_REQUEST_TIMEOUT");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        log.info("handleConstraintViolationException : {}", constraintViolationException.getMessage());
        return CustomResponse.response("FIELD_ERROR");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MappingException.class)
    public ResponseEntity<?> handleMappingException(MappingException mappingException) {
        log.info("handleMappingException : {}", mappingException.getMessage());
        return CustomResponse.response("MAPPING_ERROR");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(PersistenceException persistenceException) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("An error occurred while attempting to apply an AttributeConverter");
        log.info("handlePersistenceException: {}", persistenceException.getMessage());
        return CustomResponse.response("DEFAULT_ERROR");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        log.info("handleSQLException: {} ", sqlException.getMessage());
        return CustomResponse.response("JPA_QUERY_EXCEPTION");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        String error = methodArgumentTypeMismatchException.getName() + " should be of type " + methodArgumentTypeMismatchException.getRequiredType().getName();
        log.info("handleMethodArgumentTypeMismatch: {} ", error);
        return CustomResponse.response("METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        Map<String, String> errorsMap = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach((error) -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });
        log.info("handleMethodArgumentNotValid:{}", errorsMap.toString());
        return CustomResponse.response("METHOD_ARGUMENT_NOT_VALID_ERROR");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.info("handleHttpMessageNotReadableException : {} ", httpMessageNotReadableException.getMessage());
        return CustomResponse.response("REQUEST_FAILED");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(OrderNotFoundException orderNotFoundException) {
        log.info("handleOrderNotFoundException :{} ", orderNotFoundException.getMessage());
        return CustomResponse.response("ORDER_NOT_FOUND");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        log.info("handleCustomerNotFoundException :{} ", customerNotFoundException.getMessage());
        return CustomResponse.response("CUSTOMER_NOT_FOUND");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        log.info("handleProductNotFoundException :{} ", productNotFoundException.getMessage());
        return CustomResponse.response("PRODUCT_NOT_FOUND_ERROR");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<?> handleJpaSystemException(JpaSystemException jpaSystemException) {
        log.info("handleJpaSystemException :{} ", jpaSystemException.getMessage());
        return CustomResponse.response("JPA_QUERY_EXCEPTION");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({InvocationTargetException.class})
    public ResponseEntity<?> handleRuntimeException(InvocationTargetException invocationTargetException) {
        log.info("handleInvocationTargetException :{} ", invocationTargetException.getMessage());
        return CustomResponse.response("RUNTIME_EXCEPTION");
    }

    //IllegalStateException
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        log.info("handleIllegalArgumentException:{}", illegalArgumentException.getMessage());
        return CustomResponse.response("ILLEGAL_ARGUMENT_EXCEPTION");//ILLEGAL_ARGUMENT_EXCEPTION
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleJsonParseException(JsonParseException jsonParseException) {
        log.info("handleJsonParseException:{}", jsonParseException.getMessage());
        return CustomResponse.response("JSON_PARSE_ERROR");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(DefaultMessageException.class)
    public ResponseEntity<?> handleDefaultMessageException(DefaultMessageException defaultMessageException) {
        log.info("handleDefaultMessageException :{} ", defaultMessageException.getMessage());
        return CustomResponse.response("DEFAULT_ERROR");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException runtimeException) {
        log.info("handleRuntimeException :{} ", runtimeException.getMessage());
        return CustomResponse.response("RUNTIME_EXCEPTION");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        log.info("handleException : {} ", exception.getMessage());
        return CustomResponse.response("EXCEPTION");
    }
}
