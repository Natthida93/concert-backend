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
        // ✅ Check if user already uploaded proof for this concert
        Optional<Payment> existing = paymentRepository.findByUserAndConcert(payment.getUser(), payment.getConcert());
        if (existing.isPresent()) {
            throw new RuntimeException("Payment already submitted for this concert.");
        }

        return paymentRepository.save(payment);
    }

    // Optional separate method if needed elsewhere
    public Optional<Payment> findByUserAndConcert(User user, Concert concert) {
        return paymentRepository.findByUserAndConcert(user, concert);
    }
}
