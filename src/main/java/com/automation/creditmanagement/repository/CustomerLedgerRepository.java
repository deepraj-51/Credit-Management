package com.automation.creditmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.CustomerLedger;

public interface CustomerLedgerRepository extends JpaRepository<CustomerLedger, Long> {
        Optional<CustomerLedger> findByCustomerPhoneNumber(String phoneNumber);
}
