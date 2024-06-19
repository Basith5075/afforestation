package com.campaign.hashicorp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("postgre")
public class AppConfiguration {
    private String username;
    private String password;
    private String url;
}