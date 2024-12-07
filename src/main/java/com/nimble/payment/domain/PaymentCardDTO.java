package com.nimble.payment.domain;

public record PaymentCardDTO(String cardNumber, String expiration, String CVV) {
}
