package com.campaign.hashicorp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class VaultPropertySetter implements ApplicationRunner {

    @Autowired
    private AppConfiguration appConfiguration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.setProperty("splunk.token", appConfiguration.getToken());
        System.setProperty("splunk.url", appConfiguration.getUrl());
        System.setProperty("splunk.source", appConfiguration.getSource());
        System.setProperty("splunk.index", appConfiguration.getIndex());

    }
}