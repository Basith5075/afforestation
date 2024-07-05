package com.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignInfo {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String zipCode;
    private String state;
    private boolean status;
    private String roles;
}