package com.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.api.CampaignClient;
import com.payment.dto.CampaignTransactionDto;
import com.payment.entity.CampaignInfo;
import com.payment.entity.Payment;
import com.payment.exception.EntityNotFoundException;
import com.payment.kafka.KafkaMessagePublisher;
import com.payment.repository.PaymentRepo;
import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private CampaignClient campaignClient;

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public Payment createPayment(Payment payment) {
        
        CampaignTransactionDto campaignTransactionDto =  convertToCampaignTransaction(payment);
        try {
            sendPaymentToKafkaTopic(campaignTransactionDto);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return paymentRepo.save(payment);
    }

    private CampaignTransactionDto convertToCampaignTransaction(Payment payment) {


        Map<String, Integer> campaignSetting = new HashMap<>();
        campaignSetting.put("id",payment.getCampaignId());

        CampaignTransactionDto transactionDto = new CampaignTransactionDto();
        transactionDto.setCampaign(campaignSetting);
        transactionDto.setCustomerId(payment.getCustomerId());
        transactionDto.setAmount(payment.getAmount());
        transactionDto.setDateTime(payment.getTransactionTime());
        return transactionDto;
    }

    private void sendPaymentToKafkaTopic(CampaignTransactionDto campaignTransactionDto) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

         String campaignTransactionDtoString = objectMapper.writeValueAsString(campaignTransactionDto);
        
        try {
            kafkaMessagePublisher.sendPaymentInTopic(campaignTransactionDtoString);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Log the exception here
        }
    }


    @Override
    public String updatePayment(int id, Map<String, Object> paymentMap) {

        // Actually transaction details should not be changed. But we are doing it anyhow. If this is not acceptable,
        // we can modify this method to just update the properties such as notes

        Payment existingPayment = getPaymentById(id);

        paymentMap.forEach((key, value) -> {
            switch (key) {
                case "transactionTime":
                    existingPayment.setTransactionTime(new Date(value.toString()));
                    break;
                case "cardNumber":
                    existingPayment.setCardNumber(value.toString());
                    break;
                case "customerId":
                    existingPayment.setCustomerId((int) value);
                    break;
                case "campaignId":
                    existingPayment.setCampaignId((int) value);
                    break;
                case "amount":
                    existingPayment.setAmount(((Double) value).floatValue());
                    break;
                case "notes":
                    existingPayment.setNotes(value.toString());
                    break;
            }
        });

        paymentRepo.save(existingPayment);

        return "Successfully updated the transaction details";
    }

    @Override
    public Payment getPaymentById(int id) {
        Payment payment = paymentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found, Id : " + id));
        return payment;

    }

    @Override
    public String deletePayment(int id) {

        if (paymentRepo.existsById(id)) {
            paymentRepo.deleteById(id);
            return "Successfully deleted the transaction details, id : " + id;
        } else {
            throw new EntityNotFoundException("Transaction not found");
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = paymentRepo.findAll();
        if (payments.isEmpty()) {
            throw new EntityNotFoundException("No transactions found !!");
        }
        return payments;
    }

    @Async
    public CompletableFuture<CampaignInfo> getCampaignInfo(String email) {
        return CompletableFuture.completedFuture(campaignClient.getCampaignInfoByEmail(email));
    }


}