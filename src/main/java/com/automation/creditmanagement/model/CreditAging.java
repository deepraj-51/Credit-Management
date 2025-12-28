package com.automation.creditmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_aging")
public class CreditAging {

    @Id
    private Long customerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal days0to7 = BigDecimal.ZERO;
    private BigDecimal days8to15 = BigDecimal.ZERO;
    private BigDecimal days16to30 = BigDecimal.ZERO;
    private BigDecimal days31Plus = BigDecimal.ZERO;

    private LocalDateTime lastCalculatedAt;

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

    public BigDecimal getDays0to7() {
        return days0to7;
    }

    public void setDays0to7(BigDecimal days0to7) {
        this.days0to7 = days0to7;
    }

    public BigDecimal getDays8to15() {
        return days8to15;
    }

    public void setDays8to15(BigDecimal days8to15) {
        this.days8to15 = days8to15;
    }

    public BigDecimal getDays16to30() {
        return days16to30;
    }

    public void setDays16to30(BigDecimal days16to30) {
        this.days16to30 = days16to30;
    }

    public BigDecimal getDays31Plus() {
        return days31Plus;
    }

    public void setDays31Plus(BigDecimal days31Plus) {
        this.days31Plus = days31Plus;
    }

    public LocalDateTime getLastCalculatedAt() {
        return lastCalculatedAt;
    }

    public void setLastCalculatedAt(LocalDateTime lastCalculatedAt) {
        this.lastCalculatedAt = lastCalculatedAt;
    }
}
