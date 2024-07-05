package com.campaign.security;


import com.campaign.entity.CampaignInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CampaignInfoService implements UserDetailsService {

    @Autowired
    private CampaignInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {

        Optional<CampaignInfo> userDetail = repository.findByEmail(useremail);


        // Converting userDetail to UserDetails
        return userDetail.map(CampaignInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email Id not found " + useremail));
    }

    public String addUser(CampaignInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Campaign User Added Successfully";
    }


    public CampaignInfo getUserByEmail(String email) {

        Optional<CampaignInfo> userDetail = repository.findByEmail(email);

        return userDetail.orElseThrow(() -> new UsernameNotFoundException("Email Id not found " + email));
    }


}