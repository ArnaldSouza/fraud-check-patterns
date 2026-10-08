package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.arnaldsouza.fraudcheck.fixture.TestProperties.withBlockedAccounts;
import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.fromAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockedAccountRuleTest {

    private static final String BLOCKED_ACCOUNT_ID = "acc-blocked";

    private final BlockedAccountRule rule = new BlockedAccountRule(
            new BlockedAccountRegistry(withBlockedAccounts(BLOCKED_ACCOUNT_ID)));

    @Test
    void reportsViolationForBlockedAccount() {
        assertEquals(Optional.of("Account is blocked"), rule.findViolation(fromAccount(BLOCKED_ACCOUNT_ID)));
    }

    @Test
    void acceptsRegularAccount() {
        assertTrue(rule.findViolation(fromAccount("acc-regular")).isEmpty());
    }
}