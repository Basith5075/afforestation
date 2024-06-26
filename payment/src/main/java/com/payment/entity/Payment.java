package com.payment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    private Date transactionTime;

    private String cardNumber;

    private int customerId;

    private int campaignId;

    private float amount;

    private String notes;
}
