package com.automation.creditmanagement.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone_number")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private BigDecimal creditLimit = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // getters & setters
}
