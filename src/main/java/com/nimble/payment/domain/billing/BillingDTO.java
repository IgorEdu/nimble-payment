package com.nimble.payment.domain.billing;

import java.math.BigDecimal;

public record BillingDTO(String destinationCpf, Double amount, String description) {
}
