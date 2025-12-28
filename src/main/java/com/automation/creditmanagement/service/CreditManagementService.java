package com.automation.creditmanagement.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class CreditManagementService {

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

    /* Customer operations */
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer getCustomer(Long id) {
        return customerRepo.findById(id).orElseThrow();
    }

    public Customer getCustomerByPhone(String phone) {
        return customerRepo.findByPhoneNumber(phone).orElseThrow();
    }

    /* Ledger operations */
    public CustomerLedger getLedger(Long customerId) {
        return ledgerRepo.findById(customerId).orElseThrow();
    }

    public CustomerLedger createLedgerForCustomer(Customer customer) {
        CustomerLedger ledger = new CustomerLedger();
        ledger.setCustomer(customer);
        ledger.setTotalCredit(BigDecimal.ZERO);
        ledger.setTotalPayment(BigDecimal.ZERO);
        ledger.setCurrentBalance(BigDecimal.ZERO);
        ledger.setUpdatedAt(LocalDateTime.now());
        return ledgerRepo.save(ledger);
    }

    /* Credit transaction */
    @Transactional
    public CreditTransaction addCreditTransaction(Long customerId, BigDecimal amount, String billRef,
            String description) {
        CustomerLedger ledger = ledgerRepo.findById(customerId).orElseThrow();
        Customer customer = ledger.getCustomer();

        if (!Boolean.TRUE.equals(customer.getIsActive())) {
            throw new RuntimeException("Customer is inactive");
        }

        CreditTransaction txn = new CreditTransaction();
        txn.setCustomer(customer);
        txn.setAmount(amount);
        txn.setBillReference(billRef);
        txn.setDescription(description);
        txn.setTransactionDate(LocalDate.now());
        creditRepo.save(txn);

        ledger.setTotalCredit(ledger.getTotalCredit().add(amount));
        ledger.setCurrentBalance(ledger.getTotalCredit().subtract(ledger.getTotalPayment()));
        ledger.setLastTransactionDate(LocalDate.now());
        ledgerRepo.save(ledger);

        return txn;
    }

    /* Payment transaction */
    @Transactional
    public PaymentTransaction addPaymentTransaction(Long customerId, BigDecimal amount, String mode,
            String receivedBy) {
        CustomerLedger ledger = ledgerRepo.findById(customerId).orElseThrow();
        Customer customer = ledger.getCustomer();

        PaymentTransaction txn = new PaymentTransaction();
        txn.setCustomer(customer);
        txn.setAmount(amount);
        txn.setPaymentMode(mode);
        txn.setPaymentDate(LocalDate.now());
        txn.setReceivedBy(receivedBy);
        paymentRepo.save(txn);

        ledger.setTotalPayment(ledger.getTotalPayment().add(amount));
        ledger.setCurrentBalance(ledger.getTotalCredit().subtract(ledger.getTotalPayment()));
        ledger.setLastTransactionDate(LocalDate.now());
        ledgerRepo.save(ledger);

        return txn;
    }

    /* Queries */
    public List<CreditTransaction> listCreditsForCustomer(Long customerId) {
        return creditRepo.findByCustomerCustomerId(customerId);
    }

    public List<PaymentTransaction> listPaymentsForCustomer(Long customerId) {
        return paymentRepo.findByCustomerCustomerId(customerId);
    }

    /* Credit aging */
    public CreditAging getAging(Long customerId) {
        return agingRepo.findByCustomerCustomerId(customerId).orElseThrow();
    }

    public CreditAging upsertAging(Long customerId, BigDecimal d0to7, BigDecimal d8to15, BigDecimal d16to30,
            BigDecimal d31plus) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        CreditAging aging = agingRepo.findByCustomerCustomerId(customerId).orElse(new CreditAging());
        aging.setCustomer(customer);
        aging.setDays0to7(d0to7);
        aging.setDays8to15(d8to15);
        aging.setDays16to30(d16to30);
        aging.setDays31Plus(d31plus);
        aging.setLastCalculatedAt(LocalDateTime.now());
        return agingRepo.save(aging);
    }
}
