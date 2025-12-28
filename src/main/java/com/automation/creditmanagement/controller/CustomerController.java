package com.automation.creditmanagement.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automation.creditmanagement.model.CreditAging;
import com.automation.creditmanagement.model.CreditTransaction;
import com.automation.creditmanagement.model.Customer;
import com.automation.creditmanagement.model.CustomerLedger;
import com.automation.creditmanagement.model.PaymentTransaction;
import com.automation.creditmanagement.service.CreditManagementService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CreditManagementService service;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerRequest req) {
        Customer c = new Customer();
        c.setName(req.name);
        c.setPhoneNumber(req.phoneNumber);
        c.setAddress(req.address);
        c.setCreditLimit(req.creditLimit != null ? req.creditLimit : BigDecimal.ZERO);
        c.setIsActive(true);
        Customer saved = service.createCustomer(c);
        // create ledger
        service.createLedgerForCustomer(saved);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @GetMapping("/{id}/ledger")
    public ResponseEntity<CustomerLedger> getLedger(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLedger(id));
    }

    @PostMapping("/{id}/credits")
    public ResponseEntity<CreditTransaction> addCredit(@PathVariable Long id, @RequestBody CreditRequest req) {
        CreditTransaction txn = service.addCreditTransaction(id, req.amount, req.billReference, req.description);
        return ResponseEntity.ok(txn);
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<PaymentTransaction> addPayment(@PathVariable Long id, @RequestBody PaymentRequest req) {
        PaymentTransaction txn = service.addPaymentTransaction(id, req.amount, req.mode != null ? req.mode : "UPI",
                req.receivedBy);
        return ResponseEntity.ok(txn);
    }

    @GetMapping("/{id}/aging")
    public ResponseEntity<CreditAging> getAging(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAging(id));
    }

    static class CreateCustomerRequest {
        public String name;
        public String phoneNumber;
        public String address;
        public BigDecimal creditLimit;
    }

    static class CreditRequest {
        public BigDecimal amount;
        public String billReference;
        public String description;
    }

    static class PaymentRequest {
        public BigDecimal amount;
        public String mode;
        public String receivedBy;
    }
}
