package com.campaign.controller;

import com.campaign.entity.Campaign;
import com.campaign.entity.CampaignTransaction;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.service.CampaignTransactionService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CampaignTransactionController.class)
class CampaignTransactionControllerTest {

    @MockBean
    CampaignTransactionService campaignTransactionService;

    @Autowired
    MockMvc mockMvc;

    CampaignTransaction campaignTransaction;
    CampaignTransaction campaignTransaction1;

    Campaign campaign;

    List<CampaignTransaction> campaignTransactionList;

    private final String url = "/v1/campaignTransactions";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initializing the campaignTransaction object for testing

        campaign = new Campaign();
        campaign.setId(1);

        campaignTransaction = new CampaignTransaction();
        campaignTransaction.setId(1);
        campaignTransaction.setCampaign(campaign);
        campaignTransaction.setAmount(4500);
        campaignTransaction.setCustomerId(1);

        LocalDate localDate = LocalDate.parse("2020-01-01");
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        campaignTransaction.setDateTime(date);


        campaignTransaction1 = new CampaignTransaction();
        campaignTransaction1.setId(2);
        campaignTransaction1.setCampaign(campaign);
        campaignTransaction1.setAmount(9000);
        campaignTransaction1.setCustomerId(10);
        campaignTransaction1.setDateTime(date);

        localDate = LocalDate.parse("2022-10-01");
        date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        campaignTransaction1.setDateTime(date);

        campaignTransactionList = new ArrayList<>();

        campaignTransactionList.add(campaignTransaction);
        campaignTransactionList.add(campaignTransaction1);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void addCampaignTransaction() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(campaignTransaction);

        Mockito.when(campaignTransactionService.createCampaignTransaction(campaignTransaction)).thenReturn(campaignTransaction);

        this.mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content(jsonData))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllCampaignTransactions() throws Exception {

        Mockito.when(campaignTransactionService.getAllCampaignTransactions()).thenReturn(campaignTransactionList);

        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllCampaignTransactionsNotFound() throws Exception {

        Mockito.when(campaignTransactionService.getAllCampaignTransactions()).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getCampaignTransactionByIdFound() throws Exception {

        Mockito.when(campaignTransactionService.getCampaignTransactionById(1)).thenReturn(campaignTransaction);

        this.mockMvc.perform(get(url + "/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getCampaignTransactionByIdNotFound() throws Exception {
        Mockito.when(campaignTransactionService.getCampaignTransactionById(100)).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(get(url + "/100").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void updateCampaignTransactionFound() throws Exception {

        CampaignTransaction updatedTransactions = new CampaignTransaction();
        updatedTransactions.setAmount(5200);
        updatedTransactions.setCustomerId(5);

        campaignTransaction.setAmount(updatedTransactions.getAmount());
        campaignTransaction.setCustomerId(updatedTransactions.getCustomerId());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(updatedTransactions);

        Mockito.when(campaignTransactionService.updateCampaignTransaction(1, updatedTransactions)).thenReturn(campaignTransaction);
        this.mockMvc.perform(put(url + "?id=1").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonData)).andDo(print()).andExpect(status().isOk());

        verify(campaignTransactionService, times(1)).updateCampaignTransaction(1, updatedTransactions);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void updateCampaignTransactionNotFound() throws Exception {

        CampaignTransaction updatedTransactions = new CampaignTransaction();
        updatedTransactions.setAmount(5200);
        updatedTransactions.setCustomerId(5);


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonData = ow.writeValueAsString(updatedTransactions);

        Mockito.when(campaignTransactionService.updateCampaignTransaction(100, updatedTransactions)).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(put(url + "?id=100")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)).andDo(print())
                .andExpect(status()
                        .is4xxClientError());
        verify(campaignTransactionService, times(1)).updateCampaignTransaction(100, updatedTransactions);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deleteCampaignTransactionFound() throws Exception {

        when(campaignTransactionService.deleteCampaignTransactionById(1)).thenReturn("Transaction deleted successfully");

        String contentAsString = this.mockMvc.perform(delete(url + "/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        System.out.println(contentAsString);

        assertThat(contentAsString).isEqualTo("Transaction deleted successfully");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deleteCampaignTransactionNotFound() throws Exception {

        when(campaignTransactionService.deleteCampaignTransactionById(100)).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(delete(url + "/100").with(csrf()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is4xxClientError());

        verify(campaignTransactionService, times(1)).deleteCampaignTransactionById(100);

    }
}