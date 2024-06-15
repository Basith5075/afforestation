package com.campaign.service.impl;

import com.campaign.entity.Campaign;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.repository.CampaignRepository;
import com.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
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


    public Campaign updateCampaignPartially(Map<String, Object> campaign, int id) {

        Campaign existingCampaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + id));

        campaign.forEach((key, value) -> {
            switch (key) {
                case "campaignName":
                    existingCampaign.setCampaignName((String) value);
                    break;
                case "description":
                    existingCampaign.setDescription((String) value);
                    break;
                case "purpose":
                    existingCampaign.setPurpose((String) value);
                case "goalAmount":
                    existingCampaign.setGoalAmount(((Double)value).floatValue());
                    break;
                case "currentAmount":
                    existingCampaign.setCurrentAmount(((Double)value).floatValue());
                    break;
                // Add cases for other fields as needed...
            }
        });

        return campaignRepository.save(existingCampaign);
    }

    @Override
    public Campaign getCampaignById(int id) {

        Optional<Campaign> campaign = campaignRepository.findById(id);

        if (campaign.isPresent()){
            return campaign.get();
        }else{
           throw  new EntityNotFoundException("Campaign not found with id: " + id);
        }
    }

    @Override
    public String deleteCampaignById(int id) {
        Optional<Campaign> campaign = campaignRepository.findById(id);

        if (campaign.isPresent()){
            campaignRepository.deleteById(id);
            return "deleted the campaign with id : "+id+" successfully !!";
        }else{
            throw  new EntityNotFoundException("Campaign not found with id: " + id);
        }
    }
}