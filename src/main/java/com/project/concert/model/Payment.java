package com.project.concert.model;

import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    private String proofUrl; // ✅ updated field

    @ManyToOne
    private User user;

    @ManyToOne
    private Concert concert;

    // Getters and setters

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
