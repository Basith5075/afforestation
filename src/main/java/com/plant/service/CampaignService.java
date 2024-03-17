package com.plant.service;

import com.plant.entity.Campaign;

import java.util.List;

public interface CampaignService {

    public Campaign createCampaign(Campaign campaign);

    public List<Campaign> getCampaigns();

}
