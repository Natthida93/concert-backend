package com.project.concert.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "concert_id"})
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    private String proofUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getProofUrl() { return proofUrl; }
    public void setProofUrl(String proofUrl) { this.proofUrl = proofUrl; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Concert getConcert() { return concert; }
    public void setConcert(Concert concert) { this.concert = concert; }
}
