package com.automation.creditmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.CustomerLedger;

public interface CustomerLedgerRepository
        extends JpaRepository<CustomerLedger, Long> {
}
