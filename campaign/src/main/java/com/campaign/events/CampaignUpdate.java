package com.campaign.events;

public class CampaignUpdate {

    private int campaignId;
    private float amount;

    public CampaignUpdate(int campaignId, float amount) {
        this.campaignId = campaignId;
        this.amount = amount;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
