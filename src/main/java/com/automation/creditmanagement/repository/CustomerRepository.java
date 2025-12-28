package com.automation.creditmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
