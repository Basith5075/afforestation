package com.campaign.hashicorp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfiguration {

    @Value("${splunk.index}")
    private String index;
    @Value("${splunk.source}")
    private String source;
    @Value("${splunk.url}")
    private String url;
    @Value("${splunk.token}")
    private String token;
}