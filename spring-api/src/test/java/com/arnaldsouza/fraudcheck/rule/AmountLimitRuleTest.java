package com.arnaldsouza.fraudcheck.rule;

import org.junit.jupiter.api.Test;

import static com.arnaldsouza.fraudcheck.fixture.TestProperties.withBlockedAccounts;
import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.withAmount;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmountLimitRuleTest {

    private final AmountLimitRule rule = new AmountLimitRule(withBlockedAccounts());

    @Test
    void reportsViolationForAmountAboveLimit() {
        assertTrue(rule.findViolation(withAmount("5000.01")).isPresent());
    }

    @Test
    void acceptsAmountEqualToLimit() {
        assertTrue(rule.findViolation(withAmount("5000.00")).isEmpty());
    }
}