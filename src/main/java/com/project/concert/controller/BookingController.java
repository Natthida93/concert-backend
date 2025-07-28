package com.project.concert.controller;

import com.project.concert.model.Booking;
import com.project.concert.model.Concert;
import com.project.concert.model.User;
import com.project.concert.repository.BookingRepository;
import com.project.concert.repository.ConcertRepository;
import com.project.concert.repository.UserRepository;
import com.project.concert.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    // ✅ Prevent duplicate bookings
    @PostMapping
    public ResponseEntity<?> bookConcert(@RequestBody Map<String, Object> request) {
        try {
            String email = request.get("userEmail").toString();
            Long concertId = Long.valueOf(request.get("concertId").toString());

            Optional<User> userOpt = userRepository.findByEmail(email);
            Optional<Concert> concertOpt = concertRepository.findById(concertId);

            if (userOpt.isEmpty() || concertOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid user or concert ID.");
            }

            User user = userOpt.get();
            Concert concert = concertOpt.get();

            // ✅ Check for existing booking
            Optional<Booking> existingBooking = bookingRepository.findByUserAndConcert(user, concert);
            if (existingBooking.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("You have already booked this concert.");
            }

            // ✅ Proceed with booking
            Booking booking = bookingService.createBooking(user.getId(), concertId);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Booking failed: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/concert/{concertId}")
    public List<Booking> getBookingsByConcertId(@PathVariable Long concertId) {
        return bookingService.getBookingsByConcertId(concertId);
    }
}
