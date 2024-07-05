package com.customer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="donations")
public class CustomerDonation {

    @Id
    private int customerId;
    private int donationId;
    private String donationDate;
    private String customerAddress;
    private String customerPhone;
    private int donationAmount;
    private String cardNumber;
    private String cardType;
    private String cardExpiryDate;
    private String cardCVV;

}
