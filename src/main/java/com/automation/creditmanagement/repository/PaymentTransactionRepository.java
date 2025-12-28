package com.automation.creditmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
        List<PaymentTransaction> findByCustomerCustomerId(Long customerId);

        List<PaymentTransaction> findByPaymentDateBetween(LocalDate start, LocalDate end);
}

