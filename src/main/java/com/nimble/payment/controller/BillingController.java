package com.nimble.payment.controller;

import com.nimble.payment.domain.billing.Billing;
import com.nimble.payment.domain.billing.BillingDTO;
import com.nimble.payment.domain.billing.BillingDetailsDTO;
import com.nimble.payment.domain.user.User;
import com.nimble.payment.repositories.BillingRepository;
import com.nimble.payment.repositories.UserRepository;
import com.nimble.payment.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {
    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillingService billingService;

    @GetMapping("/sent")
    public ResponseEntity<List<BillingDetailsDTO>> getSourceBilling() {
        ResponseEntity response = billingService.getSourceBilling();

        return response;
    }

    @GetMapping("/sent/{status}")
    public ResponseEntity<List<BillingDetailsDTO>> getSourceBilling(@PathVariable @Valid String status) {
        ResponseEntity response = billingService.getSourceBillingByStatus(status);

        return response;
    }

    @GetMapping("/destination")
    public ResponseEntity<List<BillingDetailsDTO>> getDestinationBilling() {
        ResponseEntity response = billingService.getDestinationBilling();
        return response;
    }

    @GetMapping("/destination/{status}")
    public ResponseEntity<List<BillingDetailsDTO>> getDestinationBilling(@PathVariable @Valid String status) {
        ResponseEntity response = billingService.getDestinationBillingByStatus(status);
        return response;
    }


    @PostMapping("/create")
    public ResponseEntity createBilling(@RequestBody BillingDTO data) {
        ResponseEntity response = billingService.createBilling(data);

        return response;
    }
}
