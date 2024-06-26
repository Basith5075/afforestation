package com.payment.service;

import com.payment.entity.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    Payment createPayment(Payment payment);

    String updatePayment(int id, Map<String, Object> paymentMap);

    Payment getPaymentById(int id);

    String deletePayment(int id);

    List<Payment> getAllPayments();
}
