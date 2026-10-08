package com.arnaldsouza.fraudcheck.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record Transaction(String id, String accountId, BigDecimal amount, LocalDateTime timestamp) {

    public Transaction {
        requireNonBlank(id, "id");
        requireNonBlank(accountId, "accountId");
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    private static void requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }
}