package com.customer.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customer")
public class Customer {
    @Id
    private int cust_id;
    private String name;
    private String address;
    @Nullable
    private String phone;
    @Nullable
    private String email;
    private Date DOB;
    private String gender;
    private String passwd;
}
