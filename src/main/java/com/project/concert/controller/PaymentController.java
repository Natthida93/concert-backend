package com.project.concert.controller;

import com.project.concert.model.Concert;
import com.project.concert.model.Payment;
import com.project.concert.model.User;
import com.project.concert.repository.ConcertRepository;
import com.project.concert.repository.PaymentRepository;
import com.project.concert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @PostMapping
    public ResponseEntity<String> uploadProof(
            @RequestParam("proof") MultipartFile proof,
            @RequestParam("userEmail") String email,
            @RequestParam("concertId") Long concertId,
            @RequestParam("price") Double price
    ) {
        try {
            // Save uploaded file
            String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
            File uploadDir = new File(uploadDirPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + proof.getOriginalFilename();
            File savedFile = new File(uploadDir, fileName);
            proof.transferTo(savedFile);

            // ✅ Ensure user exists (create if not)
            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setRegion("Unknown"); // or receive region from frontend later
                return userRepository.save(newUser);
            });

            // ✅ Get concert from DB
            Concert concert = concertRepository.findById(concertId)
                    .orElseThrow(() -> new RuntimeException("Concert not found: " + concertId));

            // ✅ Save payment
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setConcert(concert);
            payment.setPrice(price);
            payment.setProofFileName(fileName);

            paymentRepository.save(payment);

            System.out.println("✅ Payment SAVED for: " + email + " | concertId: " + concertId);
            return ResponseEntity.ok("Payment saved");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }
}
