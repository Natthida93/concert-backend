package com.project.concert.controller;

import com.project.concert.model.Concert;
import com.project.concert.model.Payment;
import com.project.concert.model.User;
import com.project.concert.repository.ConcertRepository;
import com.project.concert.repository.UserRepository;
import com.project.concert.service.CloudinaryService;
import com.project.concert.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<String> uploadProof(
            @RequestParam("proof") MultipartFile proof,
            @RequestParam("userEmail") String email,
            @RequestParam("concertId") Long concertId,
            @RequestParam("price") Double price
    ) {
        try {
            // ✅ Check file type
            if (!proof.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image files are allowed");
            }

            // ✅ Upload file to Cloudinary
            String imageUrl = cloudinaryService.uploadFile(proof);

            // ✅ Ensure user exists
            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setRegion("Unknown");
                return userRepository.save(newUser);
            });

            // ✅ Get concert
            Concert concert = concertRepository.findById(concertId)
                    .orElseThrow(() -> new RuntimeException("Concert not found: " + concertId));

            // ✅ Check for existing payment
            Optional<Payment> existingPayment = paymentService.findByUserAndConcert(user, concert);
            if (existingPayment.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Payment already submitted for this concert.");
            }

            // ✅ Prepare and save payment
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setConcert(concert);
            payment.setPrice(price);
            payment.setProofUrl(imageUrl);

            paymentService.savePayment(payment);

            System.out.println("✅ Payment SAVED: " + email + " -> concertId: " + concertId);
            return ResponseEntity.ok("Payment saved");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading proof");
        }
    }
}
