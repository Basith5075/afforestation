//package com.payment.config;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class PaymentDataSourceConfiguration {
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.payment")
//    public DataSourceProperties paymentDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.payment.hikari")
//    public DataSource paymentDataSource() {
//        return paymentDataSourceProperties()
//                .initializeDataSourceBuilder()
//                .build();
//    }
//}
