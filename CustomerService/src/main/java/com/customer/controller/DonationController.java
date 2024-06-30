package com.customer.controller;

import com.customer.models.CustomerDonation;
import com.customer.service.CustomerDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/donation")
public class DonationController {

    @Autowired
    CustomerDonationService customerDonationService;
    @GetMapping("/donate")
    public ResponseEntity<CustomerDonation> donate(@RequestBody CustomerDonation donation) {
        CustomerDonation savedDonation = customerDonationService.donate(donation);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonation);

    }

}
