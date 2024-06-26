//package com.campaign.hashicorp;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.core.LoggerContext;
//import org.apache.logging.log4j.core.appender.ConsoleAppender;
//import org.apache.logging.log4j.core.config.Configuration;
//import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
//import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
//import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
//import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
//import org.apache.logging.log4j.core.layout.PatternLayout;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Log4j2Configuration implements ApplicationRunner {
//
//    @Autowired
//    private AppConfiguration appConfiguration;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        // Fetch secrets from Vault
//        String splunkToken = appConfiguration.getToken();
//        String splunkIndex = appConfiguration.getIndex();
//        String splunkSource = appConfiguration.getSource();
//
//        // Configure Log4j2 programmatically
//        LoggerContext context = (LoggerContext) LogManager.getContext(false);
//        Configuration config = context.getConfiguration();
//
//        // Create a new configuration builder
//        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
//
//        // Create console appender
//        LayoutComponentBuilder layout = builder.newLayout("PatternLayout")
//                .addAttribute("pattern", "%style{%d{ISO8601}} %highlight{%-5level }[%style{%t}{bright,blue}] %style{%C{10}}{bright,yellow}: %msg%n%throwable");
//
//        builder.add(builder.newAppender("console", "Console")
//                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
//                .add(layout));
//
//        // Create Http appender (SplunkHttp) programmatically
//        LayoutComponentBuilder httpLayout = builder.newLayout("PatternLayout")
//                .addAttribute("pattern", "%m");
//
//        builder.add(builder.newAppender("splunkhttp", "Http")
//                .addAttribute("url", "http://localhost:8088")
//                .addComponent(httpLayout) // Set layout
//                .addComponent(builder.newComponent("Filters")
//                        .addComponent(builder.newComponent("ThresholdFilter")
//                                .addAttribute("level", "DEBUG")))
//                .addComponent(builder.newComponent("Headers")
//                        .addComponent(builder.newComponent("Header")
//                                .addAttribute("name", "Authorization")
//                                .addAttribute("value", "Bearer " + splunkToken)))
//                .addComponent(builder.newComponent("RequestFactory")
//                        .addComponent(builder.newComponent("DefaultHttpRequestFactory")
//                                .addAttribute("connectionTimeout", "5000")
//                                .addAttribute("soTimeout", "5000")))
//        );
//
//        // Add the appenders to root logger
//        builder.add(builder.newRootLogger(org.apache.logging.log4j.Level.INFO)
//                .add(builder.newAppenderRef("console"))
//                .add(builder.newAppenderRef("splunkhttp")));
//
//        // Build and initialize configuration
//        config = builder.build();
//        context.start(config);
//    }
//}