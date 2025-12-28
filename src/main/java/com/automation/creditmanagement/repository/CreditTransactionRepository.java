package com.automation.creditmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.CreditTransaction;

public interface CreditTransactionRepository
        extends JpaRepository<CreditTransaction, Long> {
}
