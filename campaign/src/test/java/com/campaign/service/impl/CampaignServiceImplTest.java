package com.campaign.service.impl;

import com.campaign.entity.Campaign;
import com.campaign.exception.EntityNotFoundException;
import com.campaign.repository.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class CampaignServiceImplTest {

    @Spy
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private Campaign campaign;
    private Campaign campaign1;

    private List<Campaign> campaigns;

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
        campaign.setId(2);
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
    public void testCreateCampaignSuccessFull(){



        Mockito.when(campaignRepository.save(campaign)).thenReturn(campaign);

//        assertEquals(campaign,campaignService.createCampaign(campaign));

        Campaign createdCampaign = campaignService.createCampaign(campaign);

        assertThat(campaign).isEqualTo(createdCampaign);

    }

    @Test
    public void testGetCampaignSuccessFull(){
        //Given
//        Already Setup everything in the before method
        // When
        Mockito.when(campaignRepository.findAll()).thenReturn(campaigns);
        // Then
        assertThat(campaigns).isEqualTo(campaignService.getCampaigns());

        verify(campaignRepository,times(1)).findAll();
    }


    @Test
    public void testGetCampaignFail(){

        // When
        Mockito.when(campaignRepository.findAll()).thenReturn(new ArrayList<>());
        // Then
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> campaignService.getCampaigns());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, ()->campaignService.getCampaigns());
        assertThat(ex.getMessage()).isEqualTo("No Campaigns Present !!");
    }

    @Test
    public void testUpdateCampaignSuccessFull(){

        Optional opt = Optional.of(campaign);
        when(campaignRepository.findById(1)).thenReturn(opt);

        Map<String,Object> campaignMap = new HashMap<>();
        campaignMap.put("campaignName","test campaign edited");
        campaignMap.put("description","description 1 edited");

        campaign.setCampaignName(campaignMap.get("campaignName").toString());
        campaign.setDescription(campaignMap.get("description").toString());


        when(campaignRepository.save(campaign)).thenReturn(campaign);

        Campaign updatedCampaign = campaignService.updateCampaignPartially(campaignMap,1);

        verify(campaignRepository,times(1)).findById(1);

        assertThat("test campaign edited").isEqualTo(updatedCampaign.getCampaignName());
        assertThat("description 1 edited").isEqualTo(updatedCampaign.getDescription());
    }

    @Test
    public void testUpdateCampaignCampaignNotPresent(){
        Map<String,Object> campaignMap = new HashMap<>();
//        campaignMap.put("campaignId",1);
        campaignMap.put("campaignName","test campaign edited");
        campaignMap.put("description","description 1 edited");
        campaignMap.put("purpose","description edited");


//        when(campaignRepository.findById(1)).thenThrow(EntityNotFoundException.class);
        when(campaignRepository.findById(1)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(()->campaignService
                .updateCampaignPartially(campaignMap,1));

        verify(campaignRepository,times(1)).findById(1);
        verify(campaignRepository,times(0)).save(campaign);
    }


    @Test
    public void testGetCampaignByIdPresent(){

        when(campaignRepository.findById(1)).thenReturn(Optional.of(campaign));

        assertThat(campaignService.getCampaignById(1)).isEqualTo(campaign);
    }

    @Test
    public void testGetCampaignByIdNotPresent(){

        when(campaignRepository.findById(1)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(()->campaignService.getCampaignById(1));

        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> {
            campaignService.getCampaignById(1);
        });

        assertThat(entityNotFoundException.getMessage()).isEqualTo("Campaign not found with id: 1");
    }


    @Test
    public void testDeleteCampaignByIdPresent(){

        when(campaignRepository.findById(1)).thenReturn(Optional.of(campaign));

        String deleteResp = campaignService.deleteCampaignById(1);

        assertThat(deleteResp).isEqualTo("deleted the campaign with id : 1 successfully !!");
        verify(campaignRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteCampaignByIdNotPresent(){
        when(campaignRepository.findById(10)).thenReturn(Optional.empty());

        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> campaignService.deleteCampaignById(10));

        assertThat(entityNotFoundException.getMessage()).isEqualTo("Campaign not found with id: 10");
        verify(campaignRepository, times(0)).deleteById(10);
    }

}