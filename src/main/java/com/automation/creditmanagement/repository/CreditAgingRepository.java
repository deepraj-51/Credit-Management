package com.automation.creditmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.automation.creditmanagement.model.CreditAging;

public interface CreditAgingRepository extends JpaRepository<CreditAging, Long> {
	Optional<CreditAging> findByCustomerCustomerId(Long customerId);

	Optional<CreditAging> findByCustomerPhoneNumber(String phoneNumber);
}
