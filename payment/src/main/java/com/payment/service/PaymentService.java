package com.payment.service;

import com.payment.entity.CampaignInfo;
import com.payment.entity.Payment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {

    Payment createPayment(Payment payment);

    String updatePayment(int id, Map<String, Object> paymentMap);

    Payment getPaymentById(int id);

    String deletePayment(int id);

    List<Payment> getAllPayments();

    CompletableFuture<CampaignInfo> getCampaignInfo(String email);
}
