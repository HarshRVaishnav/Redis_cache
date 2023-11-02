package com.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration                                 //swagger ui implementation for exposure to application apis
@OpenAPIDefinition(
        info = @Info(
                title = "SpringBootRestApi_With_RedisCaching APIs",
                version = "v1.0",
                contact = @Contact(
                        name = "Spring Data Redis_Document (Official)", url = "https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/"
                ),
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "http://swagger.io/terms/",
                description = "This is a sample Spring_Boot_Rest_Api server created using springdocs - " +
                        "a library for OpenAPI 3 with spring boot"
        )
)
public class SwaggerConfiguration {
    @Bean
    GroupedOpenApi customerApiGroup() {
        return GroupedOpenApi.builder().group("customer").pathsToMatch("/**/customer/**").build();
    }

    @Bean
    GroupedOpenApi productApiGroup() {
        return GroupedOpenApi.builder().group("product").pathsToMatch("/**/product/**").build();
    }

    @Bean
    GroupedOpenApi orderApiGroup() {
        return GroupedOpenApi.builder().group("order").pathsToMatch("/**/order/**").build();
    }

    @Bean
    GroupedOpenApi orderDetailApiGroup() {
        return GroupedOpenApi.builder().group("orderDetails").pathsToMatch("/**/orderDetails/**").build();
    }
}