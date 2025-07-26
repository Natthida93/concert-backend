package com.project.concert.controller;

import com.project.concert.model.Concert;
import com.project.concert.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    private ConcertRepository concertRepository;

    @GetMapping
    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    @PostMapping
    public Concert createConcert(@RequestBody Concert concert) {
        return concertRepository.save(concert);
    }

    @GetMapping("/concert/{id}")
    public Concert getConcertById(@PathVariable Long id) {
        return concertRepository.findById(id).orElse(null);
    }

}
