package com.campaign.security;

import com.campaign.entity.CampaignInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer> {
Optional<CampaignInfo> findByEmail(String useremail);
}