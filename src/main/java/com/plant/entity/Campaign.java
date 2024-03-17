package com.plant.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String campaignTitle;

    private String campaignDescription;

    private Date startDate;

    private Date endDate;

    private float targetAmount;

    private float currentAmount;
}