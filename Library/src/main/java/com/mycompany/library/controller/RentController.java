/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.library.controller;



import  com.mycompany.library.Entity.Rent;
import  com.mycompany.library.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    @Autowired
    private RentRepository rentRepository;

    @GetMapping
    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    @PostMapping
    public Rent addRent(@RequestBody Rent rent) {
        return rentRepository.save(rent);
    }

}