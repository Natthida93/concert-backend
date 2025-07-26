package com.project.concert.repository;

import com.project.concert.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByConcertId(Long concertId);
}
