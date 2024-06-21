package com.campaign.repository;

import com.campaign.entity.CampaignTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignTransactionRepository extends JpaRepository<CampaignTransaction, Integer> {
}
