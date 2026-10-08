package com.arnaldsouza.fraudcheck.fixture;

import com.arnaldsouza.fraudcheck.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Factory for transactions used across tests, with safe defaults. */
public final class TestTransactions {

    private static final String DEFAULT_ACCOUNT_ID = "acc-1";
    private static final String DEFAULT_AMOUNT = "100.00";
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2026, 10, 7);
    private static final LocalTime BUSINESS_HOURS = LocalTime.of(14, 0);

    private TestTransactions() {
    }

    public static Transaction withAmount(String amount) {
        return create(DEFAULT_ACCOUNT_ID, amount, BUSINESS_HOURS);
    }

    public static Transaction atTime(int hour, int minute) {
        return create(DEFAULT_ACCOUNT_ID, DEFAULT_AMOUNT, LocalTime.of(hour, minute));
    }

    public static Transaction fromAccount(String accountId) {
        return create(accountId, DEFAULT_AMOUNT, BUSINESS_HOURS);
    }

    private static Transaction create(String accountId, String amount, LocalTime time) {
        return new Transaction("tx-1", accountId, new BigDecimal(amount),
                LocalDateTime.of(DEFAULT_DATE, time));
    }
}