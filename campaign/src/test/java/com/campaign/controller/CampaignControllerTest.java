package com.campaign.controller;

import com.campaign.entity.Campaign;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CampaignController.class)
class CampaignControllerTest {

    @MockBean
    private CampaignService campaignService;

    @Autowired
    MockMvc mockMvc;

    Campaign campaign, campaign1;

    List<Campaign> campaigns;

    private final String url = "/v1/campaign";

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


        campaign = new Campaign();
        campaign.setId(1);
        campaign.setCampaignName("test campaign");
        campaign.setDescription("description");
        campaign.setPurpose("some purpose");
        campaign.setCurrentAmount(52.5f);
        campaign.setGoalAmount(66300f);

        campaign1 = new Campaign();
        campaign1.setId(2);
        campaign1.setCampaignName("test1 campaign");
        campaign1.setDescription("description 1");
        campaign1.setPurpose("some purpose 1");
        campaign1.setCurrentAmount(566.5f);
        campaign1.setGoalAmount(677000f);

        campaigns = new ArrayList<>();

        campaigns.add(campaign);
        campaigns.add(campaign1);

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCampaignsFound() throws Exception {
        Mockito.when(campaignService.getCampaigns()).thenReturn(campaigns);

        this.mockMvc.perform(get(url + "/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCampaignsNotFound() throws Exception {
        Mockito.when(campaignService.getCampaigns()).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(get(url + "/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateCampaign() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(campaign);

        Mockito.when(campaignService.createCampaign(campaign)).thenReturn(campaign);

        this.mockMvc.perform(post(url + "/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testUpdateCampaignFound() throws Exception {

        Map<String, Object> campaignMap = new HashMap<>();

        campaignMap.put("description", "New Description");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(campaignMap);

        campaign.setDescription("New Description");

        when(campaignService.updateCampaignPartially(campaignMap, 1)).thenReturn(campaign);

        MockHttpServletResponse response = this.mockMvc.perform(patch(url + "/update/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testUpdateCampaignNotFound() throws Exception {

        Map<String, Object> campaignMap = new HashMap<>();

        campaignMap.put("description", "New Description");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(campaignMap);

        campaign.setDescription("New Description");

        when(campaignService.updateCampaignPartially(campaignMap, 22)).thenThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(patch(url + "/update/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse();
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCampaignByIdFound() throws Exception {

        when(campaignService.getCampaignById(2)).thenReturn(campaign1);

        this.mockMvc.perform(get(url + "/get/2")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCampaignByIdNotFound() throws Exception {

        when(campaignService.getCampaignById(2)).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(get(url + "/get/2")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteCampaignFound() throws Exception {
        when(campaignService.deleteCampaignById(1)).thenReturn("deleted the campaign with id : 1 successfully !!");

        String response = this.mockMvc.perform(delete(url + "/delete/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        assertEquals("deleted the campaign with id : 1 successfully !!", response);

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteCampaignNotFound() throws Exception {
        when(campaignService.deleteCampaignById(100)).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(delete(url + "/delete/100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andDo(print());
    }

}