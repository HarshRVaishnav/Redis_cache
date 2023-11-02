package com.example.customresponse;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodes {
    public static final Map<String, String> RESPONSE_CODE = new HashMap<String, String>() {{

        //SystemErrors
        put("DATA_NOT_CREATED", "ERR_RA_SYS_480");
        put("DATA_NOT_FOUND", "ERR_RA_SYS_481");
        put("REQUIRED_FIELD", "ERR_RA_SYS_482");
        put("REQUEST_FAILED", "ERR_RA_SYS_483");            //JSON request body is expected but the incoming request body is not properly formatted JSON
        put("JSON_PARSE_ERROR", "ERR_RA_SYS_484");
        put("MAPPING_ERROR", "ERR_RA_SYS_485");
        put("FIELD_ERROR", "ERR_RA_SYS_486");               //ConstraintViolationException
        put("VALIDATION_ERROR", "ERR_RA_SYS_487");
        put("JPA_QUERY_EXCEPTION", "ERR_RA_SYS_488");
        put("MODEL_NOT_FOUND", "ERR_RA_SYS_489");
        put("PAGE_NOT_FOUND", "ERR_RA_SYS_490");

        //FrameworkErrors
        put("SERVER_ERROR", "ERR_RA_FW_500");
        put("EXCEPTION", "ERR_RA_FW_500");
        put("RUNTIME_EXCEPTION", "ERR_RA_FW_501");
        put("ILLEGAL_ARGUMENT_EXCEPTION", "ERR_RA_FW_502");
        put("ATTRIBUTE_ERROR", "ERR_RA_FW_503");                     //programmers exception //when an attribute doesn't exist in an object
        put("SERVER_REQUEST_TIMEOUT", "ERR_RA_FW_504"); // In the context of Java and Spring Boot, this timeout could be due to a number of factors, such as a slow network connection, a long-running database query, or a thread deadlock.
        put("ACCESS_FORBIDDEN_ERROR", "ERR_RA_FW_505");
        put("SERVICE_UNAVAILABLE", "ERR_RA_FW_506");                //not specific to system or framework exception
        put("METHOD_ARGUMENT_NOT_VALID_ERROR", "ERR_RA_FW_511");
        put("METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION", "ERR_RA_FW_510");

        //CustomErrors
        put("UPDATE_FAILED", "ERR_RA_CUSTOM_601");
        put("DELETE_FAILED", "ERR_RA_CUSTOM_602");
        put("OUT_OF_STOCK", "ERR_RA_CUSTOM_603");
        put("ORDER_NOT_FOUND", "ERR_RA_CUSTOM_604");
        put("CUSTOMER_NOT_FOUND", "ERR_RA_CUSTOM_605");
        put("PRODUCT_NOT_FOUND_ERROR", "ERR_RA_CUSTOM_606");
        put("DEFAULT_ERROR", "ERR_RA_CUSTOM_607");
    }};
}
