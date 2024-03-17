package com.plant.controller;

import com.plant.entity.Campaign;
import com.plant.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/campaign")
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
}
