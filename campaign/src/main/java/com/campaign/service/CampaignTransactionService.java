package com.campaign.service;

import com.campaign.entity.CampaignTransaction;

import java.util.List;

public interface CampaignTransactionService {

    CampaignTransaction createCampaignTransaction(CampaignTransaction campaignTransaction);

    List<CampaignTransaction> getAllCampaignTransactions();

    CampaignTransaction getCampaignTransactionById(int id);

    CampaignTransaction updateCampaignTransaction(int id, CampaignTransaction campaignTransaction);

    String deleteCampaignTransactionById(int id);
}
