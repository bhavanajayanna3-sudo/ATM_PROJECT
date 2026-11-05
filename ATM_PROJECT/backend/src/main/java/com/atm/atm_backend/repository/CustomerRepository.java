package com.atm.atm_backend.repository;

import com.atm.atm_backend.model.Customer;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByAccountNumberAndPin(String accountNumber, String pin);

    Optional<Customer> findByAccountNumber(String accountNumber);
}
