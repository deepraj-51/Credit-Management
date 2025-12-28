package com.automation.creditmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_ledger")
public class CustomerLedger {

    @Id
    private Long customerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private BigDecimal totalCredit = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalPayment = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    private LocalDate lastTransactionDate;

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDate getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(LocalDate lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // getters & setters
}
