package com.campaign.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name ="campaign_transaction")
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CampaignTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    private int customerId;
    private float amount;
    private Date dateTime;
}