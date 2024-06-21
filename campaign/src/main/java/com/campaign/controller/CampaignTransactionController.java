package com.campaign.controller;

import com.campaign.entity.CampaignTransaction;
import com.campaign.service.CampaignTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/campaignTransactions")
public class CampaignTransactionController {

    @Autowired
    private CampaignTransactionService campaignTransactionService;

    @PostMapping
    public CampaignTransaction addCampaignTransaction(@RequestBody CampaignTransaction campaignTransaction) {
        return campaignTransactionService.createCampaignTransaction(campaignTransaction);
    }

    @GetMapping
    public List<CampaignTransaction> getAllCampaignTransactions() {

        return campaignTransactionService.getAllCampaignTransactions();
    }

    @GetMapping("/{id}")
    public CampaignTransaction getCampaignTransactionById(@PathVariable int id) {
        return campaignTransactionService.getCampaignTransactionById(id);
    }

    @PutMapping
    public CampaignTransaction updateCampaignTransaction(@RequestParam("id") int id ,@RequestBody CampaignTransaction campaignTransaction) {
        return campaignTransactionService.updateCampaignTransaction(id,campaignTransaction);
    }

    @DeleteMapping("/{id}")
    public String deleteCampaignTransaction(@PathVariable int id ) {
        return campaignTransactionService.deleteCampaignTransactionById(id);
    }

}
