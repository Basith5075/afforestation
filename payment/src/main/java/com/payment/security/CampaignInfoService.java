package com.payment.security;

import com.payment.entity.CampaignInfo;
import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CampaignInfoService implements UserDetailsService {

    @Autowired
    private PaymentService paymentService;


    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        CampaignInfo campaignInfo = paymentService.getCampaignInfo(useremail).join();
        Optional<CampaignInfo> userDetail = Optional.of(campaignInfo);
        return userDetail.map(CampaignInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email Id not found " + useremail));
    }

}