package com.automation.creditmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.CreditAging;

public interface CreditAgingRepository extends JpaRepository<CreditAging, Long> {
}
