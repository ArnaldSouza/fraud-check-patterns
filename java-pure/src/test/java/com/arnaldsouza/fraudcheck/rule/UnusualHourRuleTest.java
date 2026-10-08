package com.arnaldsouza.fraudcheck.rule;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.atTime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnusualHourRuleTest {

    private final UnusualHourRule rule = new UnusualHourRule(LocalTime.of(22, 0), LocalTime.of(6, 0));

    @Test
    void rejectsTransactionBeforeMidnightInsideWindow() {
        assertFalse(rule.check(atTime(23, 30)).isApproved());
    }

    @Test
    void rejectsTransactionAfterMidnightInsideWindow() {
        assertFalse(rule.check(atTime(3, 0)).isApproved());
    }

    @Test
    void approvesTransactionAtWindowEnd() {
        assertTrue(rule.check(atTime(6, 0)).isApproved());
    }

    @Test
    void approvesTransactionDuringBusinessHours() {
        assertTrue(rule.check(atTime(14, 0)).isApproved());
    }
}