package com.project.concert.service;

import com.project.concert.model.Booking;
import com.project.concert.model.Concert;
import com.project.concert.model.User;
import com.project.concert.repository.BookingRepository;
import com.project.concert.repository.ConcertRepository;
import com.project.concert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    public Booking createBooking(Long userId, Long concertId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        // ✅ Check for duplicate booking (user already booked this concert)
        Optional<Booking> existingBooking = bookingRepository.findByUserAndConcert(user, concert);
        if (existingBooking.isPresent()) {
            throw new RuntimeException("User has already booked this concert.");
        }

        // ✅ Save new booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setConcert(concert);
        booking.setBookingTime(LocalDateTime.now());

        System.out.println("Saving booking: " + booking);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByConcertId(Long concertId) {
        return bookingRepository.findByConcertId(concertId);
    }
}
