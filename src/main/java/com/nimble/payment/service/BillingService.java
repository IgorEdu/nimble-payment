package com.nimble.payment.service;

import com.nimble.payment.domain.billing.Billing;
import com.nimble.payment.domain.billing.BillingDTO;
import com.nimble.payment.domain.billing.BillingDetailsDTO;
import com.nimble.payment.domain.user.User;
import com.nimble.payment.repositories.BillingRepository;
import com.nimble.payment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BillingRepository billingRepository;

    public ResponseEntity getSourceBilling() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmailOrCpf(login);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Billing> billings = billingRepository.findAllBySourceUserId(user.getId());
        List<BillingDetailsDTO> billingsDetails = billings.stream()
                .map(billing -> new BillingDetailsDTO(
                        billing.getId(),
                        billing.getSourceUser().getName(),
                        billing.getSourceUser().getCpf(),
                        billing.getDestinationUser().getName(),
                        billing.getDestinationUser().getCpf(),
                        billing.getAmount(),
                        billing.getDescription(),
                        billing.getStatus()
                )).toList();

        return ResponseEntity.ok(billingsDetails);
    }

    public ResponseEntity getSourceBillingByStatus(String status){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmailOrCpf(login);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Billing> billings = billingRepository.findAllBySourceUserIdAndStatus(user.getId(), status);
        List<BillingDetailsDTO> billingsDetails = billings.stream()
                .map(billing -> new BillingDetailsDTO(
                        billing.getId(),
                        billing.getSourceUser().getName(),
                        billing.getSourceUser().getCpf(),
                        billing.getDestinationUser().getName(),
                        billing.getDestinationUser().getCpf(),
                        billing.getAmount(),
                        billing.getDescription(),
                        billing.getStatus()
                )).toList();

        return ResponseEntity.ok(billingsDetails);
    }

    public ResponseEntity getDestinationBilling() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmailOrCpf(login);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Billing> billings = billingRepository.findAllByDestinationUserId(user.getId());
        List<BillingDetailsDTO> billingsDetails = billings.stream()
                .map(billing -> new BillingDetailsDTO(
                        billing.getId(),
                        billing.getSourceUser().getName(),
                        billing.getSourceUser().getCpf(),
                        billing.getDestinationUser().getName(),
                        billing.getDestinationUser().getCpf(),
                        billing.getAmount(),
                        billing.getDescription(),
                        billing.getStatus()
                )).toList();

        return ResponseEntity.ok(billingsDetails);
    }

    public ResponseEntity getDestinationBillingByStatus(String status){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmailOrCpf(login);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Billing> billings = billingRepository.findAllByDestinationUserIdAndStatus(user.getId(), status);
        List<BillingDetailsDTO> billingsDetails = billings.stream()
                .map(billing -> new BillingDetailsDTO(
                        billing.getId(),
                        billing.getSourceUser().getName(),
                        billing.getSourceUser().getCpf(),
                        billing.getDestinationUser().getName(),
                        billing.getDestinationUser().getCpf(),
                        billing.getAmount(),
                        billing.getDescription(),
                        billing.getStatus()
                )).toList();

        return ResponseEntity.ok(billingsDetails);
    }

    public ResponseEntity createBilling(BillingDTO data) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        if (data.amount() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        User sourceUser = userRepository.findByEmailOrCpf(login);

        if (sourceUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (data.destinationCpf().equals(sourceUser.getCpf())) {
            return ResponseEntity.badRequest().build();
        }

        User destinationUser = userRepository.findByEmailOrCpf(data.destinationCpf());

        if (destinationUser == null) {
            return ResponseEntity.notFound().build();
        }


        Billing billing = new Billing(sourceUser, destinationUser, data.amount(), data.description());

        billingRepository.save(billing);

        return ResponseEntity.ok().build();
    }
}
