//package com.payment.config;
//
//import com.payment.entity.Payment;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Objects;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackageClasses = Payment.class,
//        entityManagerFactoryRef = "paymentEntityManagerFactory",
//        transactionManagerRef = "paymentTransactionManager"
//)
//public class PaymentJpaConfiguration {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean todosEntityManagerFactory(
//            @Qualifier("paymentDataSource") DataSource dataSource,
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(dataSource)
//                .packages(Payment.class)
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager todosTransactionManager(
//            @Qualifier("todosEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
//        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
//    }
//}