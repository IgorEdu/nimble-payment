package com.nimble.payment.domain.billing;

import com.nimble.payment.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "billings")
@Entity
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Billing {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_user_id", nullable = false)
    private User sourceUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_user_id", nullable = false)
    private User destinationUser;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status; // Billing Status (e.g., "PENDING", "PAID", "CANCELLED")

    public Billing() {
    }

    public Billing(User sourceUser, User destinationUser, Double amount, String description) {
        this.sourceUser = sourceUser;
        this.destinationUser = destinationUser;
        this.amount = amount;
        this.description = description;
        this.status = "PENDING";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(User sourceUser) {
        this.sourceUser = sourceUser;
    }

    public User getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(User destinationUser) {
        this.destinationUser = destinationUser;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
