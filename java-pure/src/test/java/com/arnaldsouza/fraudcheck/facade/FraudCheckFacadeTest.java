package com.arnaldsouza.fraudcheck.facade;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.atTime;
import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.create;
import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.withAmount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudCheckFacadeTest {

    private static final String BLOCKED_ACCOUNT_ID = "acc-blocked";

    private final BlockedAccountRegistry registry = BlockedAccountRegistry.getInstance();
    private final FraudCheckFacade facade = new FraudCheckFacade(registry);

    @AfterEach
    void resetRegistry() {
        registry.unblock(BLOCKED_ACCOUNT_ID);
    }

    @Test
    void approvesRegularTransaction() {
        assertTrue(facade.analyze(withAmount("250.00")).isApproved());
    }

    @Test
    void rejectsAmountAboveLimit() {
        assertEquals("Amount exceeds limit of 5000.00",
                facade.analyze(withAmount("12000.00")).reason());
    }

    @Test
    void rejectsTransactionAtUnusualHour() {
        assertEquals("Transaction at unusual hour", facade.analyze(atTime(2, 15)).reason());
    }

    @Test
    void reportsBlockedAccountFirstWhenSeveralRulesAreViolated() {
        registry.block(BLOCKED_ACCOUNT_ID);

        AnalysisResult result = facade.analyze(
                create(BLOCKED_ACCOUNT_ID, "12000.00", LocalTime.of(2, 15)));

        assertEquals("Account is blocked", result.reason());
    }
}