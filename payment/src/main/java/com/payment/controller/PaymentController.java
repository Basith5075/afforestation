package com.payment.controller;

import com.payment.entity.Payment;
import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    Logger logger = Logger.getLogger(PaymentController.class.getName());

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {

        return new ResponseEntity<Payment>(paymentService.createPayment(payment), HttpStatus.OK) ;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        return  new ResponseEntity<Payment>(paymentService.getPaymentById(id),HttpStatus.OK) ;
    }

    @DeleteMapping
    public ResponseEntity<String> deletePaymentById(@RequestParam("id") int id) {

        return new ResponseEntity<String>(paymentService.deletePayment(id),HttpStatus.OK) ;
    }

    @PutMapping
    public ResponseEntity<String> updatePayment(@RequestParam int id , @RequestBody Map<String, Object> paymentMap) {

        return new ResponseEntity<String>(paymentService.updatePayment(id,paymentMap),HttpStatus.OK) ;

    }
}
