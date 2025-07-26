package com.project.concert.controller;

import com.project.concert.model.Booking;
import com.project.concert.model.User;
import com.project.concert.repository.UserRepository;
import com.project.concert.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Booking bookConcert(@RequestBody Map<String, Object> request) {
        String email = request.get("userEmail").toString();
        Long concertId = Long.valueOf(request.get("concertId").toString());

        // Look up user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return bookingService.createBooking(user.getId(), concertId);
    }

    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/concert/{concertId}")
    public List<Booking> getBookingsByConcertId(@PathVariable Long concertId) {
        return bookingService.getBookingsByConcertId(concertId);
    }
}
