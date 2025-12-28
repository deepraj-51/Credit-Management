package com.automation.creditmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.PaymentTransaction;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransaction, Long> {
}

