package com.arnaldsouza.fraudcheck.rule;

import org.junit.jupiter.api.Test;

import static com.arnaldsouza.fraudcheck.fixture.TestProperties.withBlockedAccounts;
import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.atTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnusualHourRuleTest {

    private final UnusualHourRule rule = new UnusualHourRule(withBlockedAccounts());

    @Test
    void reportsViolationBeforeMidnightInsideWindow() {
        assertTrue(rule.findViolation(atTime(23, 30)).isPresent());
    }

    @Test
    void reportsViolationAfterMidnightInsideWindow() {
        assertTrue(rule.findViolation(atTime(3, 0)).isPresent());
    }

    @Test
    void acceptsTransactionAtWindowEnd() {
        assertTrue(rule.findViolation(atTime(6, 0)).isEmpty());
    }

    @Test
    void acceptsTransactionDuringBusinessHours() {
        assertTrue(rule.findViolation(atTime(14, 0)).isEmpty());
    }
}