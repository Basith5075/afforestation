package com.campaign.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name ="campaign_transaction")
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