package com.atm.atm_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtmController {

    @GetMapping("/")
    public String home() {
        return "Welcome to ATM Management System!";
    }
}

