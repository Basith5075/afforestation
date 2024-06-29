package com.campaign.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private int id;
=======
    private int campaignId;
>>>>>>> d8625a8 (Restructured campaign service -1)

    private String campaignName;

    private String description;

    private String purpose;

    private float goalAmount;

    private float currentAmount;
<<<<<<< HEAD

//    @OneToMany(mappedBy = "campaign",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<CampaignTransaction> transactions;
=======
>>>>>>> d8625a8 (Restructured campaign service -1)
}