package com.automation.creditmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_transactions")
public class CreditTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditTxnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String billReference;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getCreditTxnId() {
        return creditTxnId;
    }

    public void setCreditTxnId(Long creditTxnId) {
        this.creditTxnId = creditTxnId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getBillReference() {
        return billReference;
    }

    public void setBillReference(String billReference) {
        this.billReference = billReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // getters & setters
}
