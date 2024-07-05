package com.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/ping")
public class PingController {

    @GetMapping("/get")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("Hello World",HttpStatus.OK);
    }
}
