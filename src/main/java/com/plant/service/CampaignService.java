package com.plant.service;

import com.plant.entity.Campaign;

import java.util.List;
import java.util.Map;

public interface CampaignService {

    public Campaign createCampaign(Campaign campaign);

    public List<Campaign> getCampaigns();

    public Campaign updateCampaignPartially(Map<String, Object> campaign, int id);

    public Campaign getCampaignById(int id);

    public String deleteCampaignById(int id);
}
