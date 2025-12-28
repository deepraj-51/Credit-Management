package com.automation.creditmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.CreditTransaction;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Long> {
        List<CreditTransaction> findByCustomerCustomerId(Long customerId);

        List<CreditTransaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
}
