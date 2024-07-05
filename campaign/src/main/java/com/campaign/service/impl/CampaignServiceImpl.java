package com.campaign.service.impl;

import com.campaign.entity.Campaign;
import com.campaign.events.CampaignUpdate;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.repository.CampaignRepository;
import com.campaign.service.CampaignService;
import com.campaign.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements CampaignService {

    Logger logger = LogManager.getLogger(CampaignServiceImpl.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public Campaign createCampaign(Campaign campaign) {

        logger.info("CampaignServiceImpl: createCampaign invoked, request object {}", Mapper.mapToJsonString(campaign));

        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> getCampaigns() throws EntityNotFoundException {

        logger.info("CampaignServiceImpl: getCampaigns invoked");
        List<Campaign> campaignList = campaignRepository.findAll();
        if (!campaignList.isEmpty()) {
            logger.info("CampaignServiceImpl: getCampaigns reponse object: {}", Mapper.mapToJsonString(campaignList));
            return campaignList;
        } else {
            logger.error("CampaignServiceImpl: getCampaigns : encountered errors while fecthing data {}", Mapper.mapToJsonString(new EntityNotFoundException("No Campaigns Present !!")));
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
                    break;
                case "goalAmount":
                    existingCampaign.setGoalAmount(((Double) value).floatValue());
                    break;
                case "currentAmount":
                    existingCampaign.setCurrentAmount(((Double) value).floatValue());
                    break;
                case "incrementCurrentAmount":
                    existingCampaign.setCurrentAmount(existingCampaign.getCurrentAmount()+((Float) value));
                    break;
                // Add cases for other fields as needed...
            }
        });

        return campaignRepository.save(existingCampaign);
    }

    @Override
    public Campaign getCampaignById(int id) {
        logger.info("CampaignServiceImpl: getCampaignById invoked, id : {}", id);
        Optional<Campaign> campaign = campaignRepository.findById(id);

        if (campaign.isPresent()) {
            logger.info("CampaignServiceImpl: getCampaignById reponse object: {}", Mapper.mapToJsonString(campaign.get()));
            return campaign.get();
        } else {
            logger.error("CampaignServiceImpl: getCampaignById : encountered errors while fecthing data {}", Mapper.mapToJsonString(new EntityNotFoundException("Campaign not found with id: " + id)));
            throw new EntityNotFoundException("Campaign not found with id: " + id);
        }
    }

    @Override
    public String deleteCampaignById(int id) {
        logger.info("CampaignServiceImpl: deleteCampaignById invoked, id : {}", id);
        Optional<Campaign> campaign = campaignRepository.findById(id);

        if (campaign.isPresent()) {
            logger.info("CampaignServiceImpl: deleteCampaignById reponse object: deleted the campaign with id :{} successfully !! ", id);
            campaignRepository.deleteById(id);
            return "deleted the campaign with id : " + id + " successfully !!";
        } else {

            logger.error("CampaignServiceImpl: deleteCampaignById : encountered errors while fecthing data {}", Mapper.mapToJsonString(new EntityNotFoundException("Campaign not found with id: " + id)));
            throw new EntityNotFoundException("Campaign not found with id: " + id);
        }
    }

    //  Created a listener in campaignService to listen events of update in the currentAmount and update the currentAmount accordingly.
    @EventListener
    private void updateCurrentGoalAmount(CampaignUpdate campaignUpdate) {

        updateCampaignPartially(Map.of("incrementCurrentAmount", campaignUpdate.getAmount()), campaignUpdate.getCampaignId());
    }

}