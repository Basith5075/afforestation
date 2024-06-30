package com.customer.service.impl;

import com.customer.models.CustomerDonation;
import com.customer.repositories.DonationRepo;
import com.customer.service.CustomerDonationService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerDonationServiceImpl implements CustomerDonationService {

    @Autowired
    DonationRepo donationRepo;



    @Override
    public CustomerDonation donate(CustomerDonation customerDonation) {
        return donationRepo.save(customerDonation);
    }

    @KafkaListener(topics = "payment_confirm")
    public void readFromKafka(String message)
    {
        System.out.println("message from kafka received: " + message);
        JsonObject donation_object = JsonParser.parseString(message).getAsJsonObject();

        donationRepo.findById(donation_object.get("customer_id").getAsInt());

        donationRepo.save(new CustomerDonation());

    }


}
