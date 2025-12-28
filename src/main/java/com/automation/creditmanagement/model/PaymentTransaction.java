package com.automation.creditmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTxnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String paymentMode;

    @Column(nullable = false)
    private LocalDate paymentDate;

    private String receivedBy;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getPaymentTxnId() {
        return paymentTxnId;
    }

    public void setPaymentTxnId(Long paymentTxnId) {
        this.paymentTxnId = paymentTxnId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // getters & setters
}
