package com.automation.creditmanagement.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.automation.creditmanagement.model.CreditAging;
import com.automation.creditmanagement.model.CreditTransaction;
import com.automation.creditmanagement.model.Customer;
import com.automation.creditmanagement.model.CustomerLedger;
import com.automation.creditmanagement.model.PaymentTransaction;
import com.automation.creditmanagement.repository.CreditAgingRepository;
import com.automation.creditmanagement.repository.CreditTransactionRepository;
import com.automation.creditmanagement.repository.CustomerLedgerRepository;
import com.automation.creditmanagement.repository.CustomerRepository;
import com.automation.creditmanagement.repository.PaymentTransactionRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CustomerLedgerRepository ledgerRepo;
    
    @Autowired
    private CreditTransactionRepository creditRepo;

    @Autowired
    private PaymentTransactionRepository paymentRepo;

    @Autowired
    private CreditAgingRepository agingRepo;

    @Override
    public void run(String... args) throws Exception {
        createIfNotExists("Alice", "9999999991", "123 Main St", new BigDecimal("5000"));
        createIfNotExists("Bob", "9999999992", "456 Market Ave", new BigDecimal("10000"));
    }

    private void createIfNotExists(String name, String phone, String address, BigDecimal creditLimit) {
        if (customerRepo.findByPhoneNumber(phone).isPresent()) {
            return;
        }

        Customer c = new Customer();
        c.setName(name);
        c.setPhoneNumber(phone);
        c.setAddress(address);
        c.setCreditLimit(creditLimit);
        c.setIsActive(true);

        customerRepo.save(c);

        CustomerLedger ledger = new CustomerLedger();
        ledger.setCustomer(c);
        ledger.setTotalCredit(BigDecimal.ZERO);
        ledger.setTotalPayment(BigDecimal.ZERO);
        ledger.setCurrentBalance(BigDecimal.ZERO);

        ledgerRepo.save(ledger);

        // Create a sample credit transaction
        CreditTransaction credit = new CreditTransaction();
        credit.setCustomer(c);
        credit.setAmount(new BigDecimal("1000"));
        credit.setTransactionDate(java.time.LocalDate.now());
        credit.setBillReference("INIT-BILL-" + phone);
        creditRepo.save(credit);

        // Create a sample payment transaction
        PaymentTransaction payment = new PaymentTransaction();
        payment.setCustomer(c);
        payment.setAmount(new BigDecimal("200"));
        payment.setPaymentMode("UPI");
        payment.setPaymentDate(java.time.LocalDate.now());
        paymentRepo.save(payment);

        // Update ledger totals to reflect sample transactions
        ledger.setTotalCredit(ledger.getTotalCredit().add(credit.getAmount()));
        ledger.setTotalPayment(ledger.getTotalPayment().add(payment.getAmount()));
        ledger.setCurrentBalance(ledger.getTotalCredit().subtract(ledger.getTotalPayment()));
        ledger.setLastTransactionDate(java.time.LocalDate.now());
        ledgerRepo.save(ledger);

        // Create a sample CreditAging entry
        CreditAging aging = new CreditAging();
        aging.setCustomer(c);
        aging.setDays0to7(new BigDecimal("800"));
        aging.setDays8to15(new BigDecimal("100"));
        aging.setDays16to30(new BigDecimal("50"));
        aging.setDays31Plus(new BigDecimal("50"));
        aging.setLastCalculatedAt(java.time.LocalDateTime.now());
        agingRepo.save(aging);
    }
}
