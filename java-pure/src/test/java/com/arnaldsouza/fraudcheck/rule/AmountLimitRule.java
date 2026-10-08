package com.arnaldsouza.fraudcheck.rule;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.withAmount;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmountLimitRuleTest {

    private final AmountLimitRule rule = new AmountLimitRule(new BigDecimal("5000.00"));

    @Test
    void rejectsAmountAboveLimit() {
        assertFalse(rule.check(withAmount("5000.01")).isApproved());
    }

    @Test
    void approvesAmountEqualToLimit() {
        assertTrue(rule.check(withAmount("5000.00")).isApproved());
    }
}