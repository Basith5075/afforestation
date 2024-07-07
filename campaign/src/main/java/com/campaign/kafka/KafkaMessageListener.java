package com.campaign.kafka;

import com.campaign.entity.CampaignTransaction;
import com.campaign.service.CampaignTransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @Autowired
    private CampaignTransactionService campaignTransactionService;

    @KafkaListener(topics = "payments-received-1")
    public void consume(String receivedCampaignTransaction) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CampaignTransaction campaignTransaction = mapper.readValue(receivedCampaignTransaction, CampaignTransaction.class);
        campaignTransactionService.createCampaignTransaction(campaignTransaction);
    }
}