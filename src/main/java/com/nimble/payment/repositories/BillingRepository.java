package com.nimble.payment.repositories;

import com.nimble.payment.domain.billing.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BillingRepository extends JpaRepository<Billing, UUID> {
    List<Billing> findAllBySourceUserId(UUID id);
    List<Billing> findAllByDestinationUserId(UUID id);

    List<Billing> findAllBySourceUserIdAndStatus(UUID id, String status);
    List<Billing> findAllByDestinationUserIdAndStatus(UUID id, String status);
}