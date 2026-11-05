package com.atm.atm_backend.controller;

import com.atm.atm_backend.model.Customer;
import com.atm.atm_backend.repository.CustomerRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Customer Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {

        Optional<Customer> result =
                customerRepository.findByAccountNumberAndPin(
                        customer.getAccountNumber(),
                        customer.getPin());

        if (result.isPresent()) {
            Customer loggedInCustomer = result.get();
            loggedInCustomer.setPin(null);
            return ResponseEntity.ok(loggedInCustomer);
        }

        return ResponseEntity.status(401).body("Invalid Account Number or PIN");
    }

    // Check Balance
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<?> checkBalance(@PathVariable String accountNumber) {

        Optional<Customer> result =
                customerRepository.findByAccountNumber(accountNumber);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get().getBalance());
        }

        return ResponseEntity.notFound().build();
    }
    // Deposit Money
@PostMapping("/deposit/{accountNumber}/{amount}")
public ResponseEntity<?> deposit(
        @PathVariable String accountNumber,
        @PathVariable double amount) {

    Optional<Customer> result =
            customerRepository.findByAccountNumber(accountNumber);

    if (result.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    if (amount <= 0) {
        return ResponseEntity.badRequest().body("Invalid amount");
    }

    Customer customer = result.get();
    customer.setBalance(customer.getBalance() + amount);

    customerRepository.save(customer);

    return ResponseEntity.ok("Deposit Successful! New Balance: " + customer.getBalance());
}

// Withdraw Money
@PostMapping("/withdraw/{accountNumber}/{amount}")
public ResponseEntity<?> withdraw(
        @PathVariable String accountNumber,
        @PathVariable double amount) {

    Optional<Customer> result =
            customerRepository.findByAccountNumber(accountNumber);

    if (result.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    if (amount <= 0) {
        return ResponseEntity.badRequest().body("Invalid amount");
    }

    Customer customer = result.get();

    if (customer.getBalance() < amount) {
        return ResponseEntity.badRequest().body("Insufficient Balance");
    }

    customer.setBalance(customer.getBalance() - amount);

    customerRepository.save(customer);

    return ResponseEntity.ok("Withdrawal Successful! New Balance: " + customer.getBalance());
}
}