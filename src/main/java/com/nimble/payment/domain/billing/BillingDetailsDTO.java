package com.nimble.payment.domain.billing;

import java.util.UUID;

public record BillingDetailsDTO(UUID id, String sourceName, String sourceCpf, String destinationName, String destinationCpf, Double amount, String description, String status) {

}
