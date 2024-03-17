package com.plant.service.impl;

import com.plant.entity.Campaign;
import com.plant.exception.EntityNotFoundException;
import com.plant.repository.CampaignRepository;
import com.plant.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;
    @Override
    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> getCampaigns() throws EntityNotFoundException{
       List<Campaign> campaignList = campaignRepository.findAll();

       if(!campaignList.isEmpty()){
           return campaignList;
       }else {
           throw new EntityNotFoundException("No Campaigns Present !!");
       }
    }
}