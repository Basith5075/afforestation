package com.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignTransactionDto {

    private Map<String,Integer> campaign;
    private int customerId;
    private float amount;
    private Date dateTime;
}
