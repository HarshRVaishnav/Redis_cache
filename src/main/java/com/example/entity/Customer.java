package com.example.entity;

import com.example.customvalidation.PostalCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "customer_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerNumber")
    private Integer customerNumber;

    @Size(min = 3, max = 25, message = "Customer First Name Must be between 3 to 15")
    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[A-Z a-z]*$", message = "Invalid Input")
    private String customerFirstName;

    @Size(min = 3, max = 25, message = "Customer Last Name Must be between 3 to 15")
    @NotBlank(message = "Last name cannot be blank")
    @Pattern(regexp = "^[A-Z a-z]*$", message = "Invalid Input")
    private String customerLastName;

    @Pattern(regexp = "^[0-9]{10}", message = "Invalid Mobile Number")
    private String phone;

    @NotBlank(message = "Address line 1 cannot be blank")
    @Size(max = 50, message = "Address line 1 cannot be more than 50 characters")
    private String addressLine1;

    @Size(max = 50, message = "Address line 2 cannot be more than 50 characters")
    private String addressLine2;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @PostalCode
    private Integer postalCode;

    @NotBlank(message = "Country cannot be blank")
    private String country;

}