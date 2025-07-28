package com.project.concert.service;

import com.project.concert.model.Concert;
import com.project.concert.model.Payment;
import com.project.concert.model.User;
import com.project.concert.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // this method to check for existing payment
    public Optional<Payment> findByUserAndConcert(User user, Concert concert) {
        return paymentRepository.findByUserAndConcert(user, concert);
    }
}
