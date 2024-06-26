//package com.campaign.hashicorp;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class VaultPropertyGetter implements ApplicationRunner {
//
//    private final Environment environment;
//
//    public VaultPropertyGetter(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        String splunkToken = environment.getProperty("splunk.token");
//        String splunkIndex = environment.getProperty("splunk.index");
//        String splunkSource = environment.getProperty("splunk.source");
//        String splunkUrl = environment.getProperty("splunk.url");
//
//        System.out.println("Splunk Token: " + splunkToken);
//        System.out.println("Splunk Index: " + splunkIndex);
//        System.out.println("Splunk Source: " + splunkSource);
//        System.out.println("Splunk Url: " + splunkUrl);
//    }
//
//}