package com.campaign.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String campaignName;

    private String description;

    private String purpose;

    private float goalAmount;

    private float currentAmount;

//    @OneToMany(mappedBy = "campaign",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<CampaignTransaction> transactions;
}