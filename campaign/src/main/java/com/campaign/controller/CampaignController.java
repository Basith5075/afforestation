package com.campaign.controller;

import com.campaign.entity.Campaign;
import com.campaign.service.CampaignService;
<<<<<<< HEAD
import com.campaign.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
=======
>>>>>>> d8625a8 (Restructured campaign service -1)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
<<<<<<< HEAD
@RequestMapping(value = "/v1/campaign")
public class CampaignController {

    Logger logger = LogManager.getLogger(CampaignController.class);

=======
@RequestMapping(value = "/campaign")
public class CampaignController {

>>>>>>> d8625a8 (Restructured campaign service -1)
    @Autowired
    private CampaignService campaignService;

    @GetMapping("/get")
<<<<<<< HEAD
    public ResponseEntity<List<Campaign>> getCampaigns() {
        logger.info("CampaignController: getCampaigns invoked: response object {}:", Mapper.mapToJsonString(campaignService.getCampaigns()));
        return new ResponseEntity<>(campaignService.getCampaigns(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public Campaign createCampaign(@RequestBody Campaign campaign) {

        logger.info("CampaignController: createCampaign invoked: request object {}", Mapper.mapToJsonString(campaign));
=======
    public ResponseEntity<List<Campaign>> getCampaigns(){

        return new ResponseEntity<>(campaignService.getCampaigns(),HttpStatus.OK);
    }

    @PostMapping("/create")
    public Campaign createCampaign(@RequestBody Campaign campaign){


>>>>>>> d8625a8 (Restructured campaign service -1)
        return campaignService.createCampaign(campaign);
    }

    // update

    @PatchMapping("/update/{id}")
<<<<<<< HEAD
    public Campaign updateCampaign(@PathVariable int id, @RequestBody Map<String, Object> campaign) {
        return campaignService.updateCampaignPartially(campaign, id);
    }

    @GetMapping("/get/{id}")
    public Campaign getCampaignById(@PathVariable int id) {
        logger.info("CampaignController: getCampaignById invoked: request id {}", id);
=======
    public Campaign updateCampaign(@PathVariable int id, @RequestBody Map<String, Object> campaign){
        return campaignService.updateCampaignPartially(campaign,id);
    }

    @GetMapping("/get/{id}")
    public Campaign getCampaignById(@PathVariable int id){

>>>>>>> d8625a8 (Restructured campaign service -1)
        return campaignService.getCampaignById(id);
    }


    @DeleteMapping("/delete/{id}")
<<<<<<< HEAD
    public String deleteCampaignById(@PathVariable int id) {
        logger.info("CampaignController: deleteCampaignById invoked: request id {}", id);
=======
    public String deleteCampaignById(@PathVariable int id){
>>>>>>> d8625a8 (Restructured campaign service -1)
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
