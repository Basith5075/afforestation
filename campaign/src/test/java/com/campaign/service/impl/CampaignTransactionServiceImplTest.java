package com.campaign.service.impl;

import com.campaign.entity.Campaign;
import com.campaign.entity.CampaignTransaction;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.repository.CampaignTransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CampaignTransactionServiceImplTest {

    @Mock
    private CampaignTransactionRepository campaignTransactionRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private CampaignTransactionServiceImpl campaignTransactionService;

    CampaignTransaction campaignTransaction;
    CampaignTransaction campaignTransaction1;

    Campaign campaign;

    List<CampaignTransaction> campaignTransactionList;

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

    @AfterEach
    void tearDown() {

    }


    @Test
    void createCampaignTransaction() {

        Mockito.when(campaignTransactionRepository.save(campaignTransaction)).thenReturn(campaignTransaction);

        CampaignTransaction campaignTransaction1 = campaignTransactionService.createCampaignTransaction(campaignTransaction);

        assertThat(campaignTransaction1.getAmount()).isEqualTo(4500);
        assertThat(campaignTransaction1.getCampaign().getId()).isEqualTo(1);
        assertThat(campaignTransaction1.getDateTime()).isEqualTo("2020-01-01");
        assertThat(campaignTransaction1.getCustomerId()).isEqualTo(1);
        assertThat(campaignTransaction1.getId()).isEqualTo(1);
    }


    @Test
    void getAllCampaignTransactionsFound() {

        Mockito.when(campaignTransactionRepository.findAll()).thenReturn(campaignTransactionList);

        List<CampaignTransaction> campaignTransactionList = campaignTransactionService.getAllCampaignTransactions();

        assertThat(campaignTransactionList.size()).isEqualTo(2);
        assertThat(campaignTransactionList.get(0).getId()).isEqualTo(1);
        assertThat(campaignTransactionList.get(1).getId()).isEqualTo(2);
        assertThat(campaignTransactionList.get(0).getAmount()).isEqualTo(4500);
        assertThat(campaignTransactionList.get(1).getAmount()).isEqualTo(9000);
    }

    @Test
    void getAllCampaignTransactionsNotFound() {

        Mockito.when(campaignTransactionRepository.findAll()).thenReturn(new ArrayList<>());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> campaignTransactionService.getAllCampaignTransactions());

        assertThat(exception.getMessage()).isEqualTo("Campaign transactions not found");

    }


    @Test
    void getCampaignTransactionByIdFound() {
        Mockito.when(campaignTransactionRepository.findById(1)).thenReturn(Optional.of(campaignTransaction));

        CampaignTransaction campaignTransaction = campaignTransactionService.getCampaignTransactionById(1);

        assertThat(campaignTransaction.getId()).isEqualTo(1);
        assertThat(campaignTransaction.getAmount()).isEqualTo(4500);
        assertThat(campaignTransaction.getCustomerId()).isEqualTo(1);
        assertThat(campaignTransaction.getCampaign().getId()).isEqualTo(1);
    }

    @Test
    void getCampaignTransactionByIdNotFound() {

        Mockito.when(campaignTransactionRepository.findById(1)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> campaignTransactionService.getCampaignTransactionById(1));

        verify(campaignTransactionRepository, times(1)).findById(1);

    }

    @Test
    void updateCampaignTransactionFound() {
        CampaignTransaction newTransaction = new CampaignTransaction();

        Mockito.when(campaignTransactionRepository.findById(1)).thenReturn(Optional.of(campaignTransaction));

        CampaignTransaction updateCampaignTransaction = new CampaignTransaction();
        updateCampaignTransaction.setCustomerId(200);
        updateCampaignTransaction.setAmount(1500);
        campaign.setId(12);
        updateCampaignTransaction.setCampaign(campaign);

        LocalDate localDate = LocalDate.parse("2020-01-01");

        localDate = LocalDate.parse("2025-10-01");
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        updateCampaignTransaction.setDateTime(date);

        CampaignTransaction updatedCampaignTransaction = campaignTransactionService.updateCampaignTransaction(1, updateCampaignTransaction);

        assertThat(updatedCampaignTransaction.getAmount()).isEqualTo(1500);
        assertThat(updatedCampaignTransaction.getCustomerId()).isEqualTo(200);
        assertThat(updatedCampaignTransaction.getCampaign().getId()).isEqualTo(12);
        assertThat(updatedCampaignTransaction.getDateTime()).isEqualTo("2025-10-01");
    }

    @Test
    void updateCampaignTransactionNotFound() {

        Mockito.when(campaignTransactionRepository.findById(60)).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> campaignTransactionService.updateCampaignTransaction(60, campaignTransaction));
        verify(campaignTransactionRepository, times(0)).save(campaignTransaction);
    }

    @Test
    void deleteCampaignTransactionByIdFound() {

        when(campaignTransactionRepository.existsById(1)).thenReturn(true);

        assertThat(campaignTransactionService.deleteCampaignTransactionById(1)).isEqualTo("Transaction deleted successfully");
        verify(campaignTransactionRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCampaignTransactionByIdNotFound() {

        when(campaignTransactionRepository.existsById(100)).thenReturn(false);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(()->campaignTransactionService.deleteCampaignTransactionById(100));

        verify(campaignTransactionRepository, times(0)).deleteById(100);
        verify(campaignTransactionRepository, times(1)).existsById(100);

    }
}