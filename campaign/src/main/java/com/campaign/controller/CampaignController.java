package com.campaign.controller;

import com.campaign.entity.Campaign;
import com.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/get")
    public ResponseEntity<List<Campaign>> getCampaigns(){

        return new ResponseEntity<>(campaignService.getCampaigns(),HttpStatus.OK);
    }

    @PostMapping("/create")
    public Campaign createCampaign(@RequestBody Campaign campaign){
        return campaignService.createCampaign(campaign);
    }

    // update

    @PatchMapping("/update/{id}")
    public Campaign updateCampaign(@PathVariable int id, @RequestBody Map<String, Object> campaign){
        return campaignService.updateCampaignPartially(campaign,id);
    }

    @GetMapping("/get/{id}")
    public Campaign getCampaignById(@PathVariable int id){

        return campaignService.getCampaignById(id);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteCampaignById(@PathVariable int id){
        return campaignService.deleteCampaignById(id);
    }

    // getById


    //
    // Validate key. Only returns role of the person.. Key in header

    // 1. Show all the fields (PreAuthorization -- Manager)
    // 2. Show Limited fields (PreAuthorization -- User)

    // getUserRole() -- Will take care of user authorization -- If the role is user
    // getBy
}
