package com.arnaldsouza.fraudcheck.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2026, 10, 7, 14, 0);

    @Test
    void rejectsNonPositiveAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("tx-1", "acc-1", BigDecimal.ZERO, TIMESTAMP));
    }

    @Test
    void rejectsBlankAccountId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("tx-1", " ", new BigDecimal("100.00"), TIMESTAMP));
    }
}