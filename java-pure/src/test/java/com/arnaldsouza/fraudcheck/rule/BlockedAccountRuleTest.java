package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.fromAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockedAccountRuleTest {

    private static final String BLOCKED_ACCOUNT_ID = "acc-blocked";

    private final BlockedAccountRegistry registry = BlockedAccountRegistry.getInstance();
    private final BlockedAccountRule rule = new BlockedAccountRule(registry);

    @AfterEach
    void resetRegistry() {
        registry.unblock(BLOCKED_ACCOUNT_ID);
    }

    @Test
    void rejectsTransactionFromBlockedAccount() {
        registry.block(BLOCKED_ACCOUNT_ID);

        assertEquals("Account is blocked", rule.check(fromAccount(BLOCKED_ACCOUNT_ID)).reason());
    }

    @Test
    void approvesTransactionFromRegularAccount() {
        assertTrue(rule.check(fromAccount("acc-regular")).isApproved());
    }
}