package com.automation.creditmanagement.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.automation.creditmanagement.model.CreditTransaction;
import com.automation.creditmanagement.model.Customer;
import com.automation.creditmanagement.model.CustomerLedger;
import com.automation.creditmanagement.model.PaymentTransaction;
import com.automation.creditmanagement.repository.CreditTransactionRepository;
import com.automation.creditmanagement.repository.CustomerLedgerRepository;
import com.automation.creditmanagement.repository.PaymentTransactionRepository;

@Service
public class service {

    @Autowired
    private CreditTransactionRepository creditRepo;

    @Autowired
    private PaymentTransactionRepository paymentRepo;

    @Autowired
    private CustomerLedgerRepository ledgerRepo;

    @Transactional
    public void addCredit(Long customerId, BigDecimal amount) {

        CustomerLedger ledger = ledgerRepo.findById(customerId)
                .orElseThrow();

        Customer customer = ledger.getCustomer();

        if (!customer.getIsActive()) {
            throw new RuntimeException("Customer is inactive");
        }

        if (ledger.getCurrentBalance().add(amount)
                .compareTo(customer.getCreditLimit()) > 0) {
            throw new RuntimeException("Credit limit exceeded");
        }

        CreditTransaction txn = new CreditTransaction();
        txn.setCustomer(customer);
        txn.setAmount(amount);
        txn.setTransactionDate(LocalDate.now());

        creditRepo.save(txn);

        ledger.setTotalCredit(ledger.getTotalCredit().add(amount));
        ledger.setCurrentBalance(
                ledger.getTotalCredit().subtract(ledger.getTotalPayment()));
        ledger.setLastTransactionDate(LocalDate.now());

        ledgerRepo.save(ledger);
    }

    @Transactional
    public void recordPayment(Long customerId, BigDecimal amount) {

        CustomerLedger ledger = ledgerRepo.findById(customerId)
                .orElseThrow();

        PaymentTransaction txn = new PaymentTransaction();
        txn.setCustomer(ledger.getCustomer());
        txn.setAmount(amount);
        txn.setPaymentMode("UPI");
        txn.setPaymentDate(LocalDate.now());

        paymentRepo.save(txn);

        ledger.setTotalPayment(ledger.getTotalPayment().add(amount));
        ledger.setCurrentBalance(
                ledger.getTotalCredit().subtract(ledger.getTotalPayment()));
        ledger.setLastTransactionDate(LocalDate.now());

        ledgerRepo.save(ledger);
    }
}
