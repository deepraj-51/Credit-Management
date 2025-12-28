package com.automation.creditmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automation.creditmanagement.model.CreditAging;
import com.automation.creditmanagement.service.CreditManagementService;

@RestController
@RequestMapping("/api/aging")
public class CreditAgingController {

    @Autowired
    private CreditManagementService service;

    @GetMapping
    public ResponseEntity<List<CreditAging>> listAging() {
        return ResponseEntity.ok(service.listAllAging());
    }
}
