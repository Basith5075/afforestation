package com.payment.kafka;

import com.payment.dto.CampaignTransactionDto;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void sendPaymentInTopic(String campaignTransactionDtoString){

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("payments-received", campaignTransactionDtoString);

        future.whenComplete((result,ex) ->{
            if(ex == null ){
                System.out.println("[Send the payment information of $ " + result + "] with offset " + result.getRecordMetadata().offset());
            }else{
                System.out.println("Unable to send payment due to : " + ex.getMessage());
            }
        });

    }
}
