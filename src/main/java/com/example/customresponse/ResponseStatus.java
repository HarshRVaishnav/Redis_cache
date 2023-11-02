package com.example.customresponse;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ResponseStatus {
    public static final Map<String, HttpStatus> STATUS_CODE = new HashMap<String, HttpStatus>() {{
        //success Codes
        put("SUCCESS", HttpStatus.OK);
        put("UPDATED", HttpStatus.OK);
        put("CREATED", HttpStatus.CREATED);

        //SystemErrors
        put("DATA_NOT_CREATED", HttpStatus.INTERNAL_SERVER_ERROR);
        put("DATA_NOT_FOUND", HttpStatus.NOT_FOUND);
        put("REQUIRED_FIELD", HttpStatus.UNPROCESSABLE_ENTITY);
        put("REQUEST_FAILED", HttpStatus.BAD_REQUEST);
        put("JSON_PARSE_ERROR", HttpStatus.UNPROCESSABLE_ENTITY);
        put("MAPPING_ERROR", HttpStatus.UNPROCESSABLE_ENTITY);
        put("FIELD_ERROR", HttpStatus.BAD_REQUEST);
        put("VALIDATION_ERROR", HttpStatus.BAD_REQUEST);
        put("JPA_QUERY_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR);
        put("MODEL_NOT_FOUND", HttpStatus.NOT_FOUND);
        put("PAGE_NOT_FOUND", HttpStatus.NOT_FOUND);

        //FrameworkErrors
        put("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        put("EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR);
        put("RUNTIME_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR);
        put("ILLEGAL_ARGUMENT_EXCEPTION", HttpStatus.UNPROCESSABLE_ENTITY);
        put("ATTRIBUTE_ERROR",HttpStatus.BAD_REQUEST);
        put("SERVER_REQUEST_TIMEOUT", HttpStatus.INTERNAL_SERVER_ERROR);
        put("ACCESS_FORBIDDEN_ERROR", HttpStatus.FORBIDDEN);
        put("SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE);
        put("METHOD_ARGUMENT_NOT_VALID_ERROR", HttpStatus.BAD_REQUEST);
        put("METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION", HttpStatus.BAD_REQUEST);

        //CustomErrors
        put("UPDATE_FAILED", HttpStatus.NOT_MODIFIED);
        put("DELETE_FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        put("OUT_OF_STOCK", HttpStatus.BAD_REQUEST);
        put("ORDER_NOT_FOUND", HttpStatus.NOT_FOUND);
        put("CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND);
        put("PRODUCT_NOT_FOUND_ERROR", HttpStatus.NOT_FOUND);
        put("DEFAULT_ERROR", HttpStatus.BAD_REQUEST);
    }};
}