package com.project.concert.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to User
    private User user;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false) // Foreign key to Concert
    private Concert concert;

    private LocalDateTime bookingTime;

    // === GETTERS ===
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Concert getConcert() {
        return concert;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    // === SETTERS ===
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
