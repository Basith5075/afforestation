package com.plant.service;

import com.plant.entity.Campaign;

import java.util.List;
import java.util.Map;

 public interface CampaignService {

     Campaign createCampaign(Campaign campaign);

     List<Campaign> getCampaigns();

     Campaign updateCampaignPartially(Map<String, Object> campaign, int id);

     Campaign getCampaignById(int id);

     String deleteCampaignById(int id);
}
