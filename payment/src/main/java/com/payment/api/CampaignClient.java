package com.payment.api;

import com.payment.entity.CampaignInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "campaignService", url = "http://localhost:8009")
public interface CampaignClient {

    @GetMapping("/auth/getCampaignInfoByEmail")
    CampaignInfo getCampaignInfoByEmail(@RequestParam("email") String email);
}