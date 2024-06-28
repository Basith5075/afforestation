package com.campaign.service.impl;

import com.campaign.entity.CampaignTransaction;
import com.campaign.events.CampaignUpdate;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.repository.CampaignTransactionRepository;
import com.campaign.service.CampaignTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignTransactionServiceImpl implements CampaignTransactionService {

    @Autowired
    private CampaignTransactionRepository campaignTransactionRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public CampaignTransaction createCampaignTransaction(CampaignTransaction campaignTransaction) {

        // publishing the event of updating the currentAmount in Campaign class
        applicationEventPublisher.publishEvent(new CampaignUpdate(campaignTransaction.getCampaign().getId(), campaignTransaction.getAmount()));

        return campaignTransactionRepository.save(campaignTransaction);
    }


    @Override
    public List<CampaignTransaction> getAllCampaignTransactions() {

        List<CampaignTransaction> campaignTransactionList = campaignTransactionRepository.findAll();

        if (campaignTransactionList.isEmpty()) {
            throw new EntityNotFoundException("Campaign transactions not found");
        } else {
            return campaignTransactionList;
        }
    }

    @Override
    public CampaignTransaction getCampaignTransactionById(int id) {

        CampaignTransaction campaignTransaction = campaignTransactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Campaign transactions not found with Id:" + id));

        return campaignTransaction;
    }

    @Override
    public CampaignTransaction updateCampaignTransaction(int id, CampaignTransaction campaignTransaction) {

        CampaignTransaction existingTransaction = this.getCampaignTransactionById(id);

        if (campaignTransaction.getCustomerId() != 0) {
            existingTransaction.setCustomerId(campaignTransaction.getCustomerId());
        }
        if (campaignTransaction.getCampaign() != null) {
            existingTransaction.setCampaign(campaignTransaction.getCampaign());
        }
        if (campaignTransaction.getAmount() != 0.0) {
            existingTransaction.setAmount(campaignTransaction.getAmount());
        }
        if (campaignTransaction.getDateTime() != null) {
            existingTransaction.setDateTime(campaignTransaction.getDateTime());
        }

        campaignTransactionRepository.save(existingTransaction);
        return existingTransaction;
    }

    @Override
    public String deleteCampaignTransactionById(int id) {
        if (campaignTransactionRepository.existsById(id)) {
            campaignTransactionRepository.deleteById(id);
            return "Transaction deleted successfully";
        } else {
            throw new EntityNotFoundException("Campaign transactions not found with Id:" + id);
        }
    }
}
