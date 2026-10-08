package com.arnaldsouza.fraudcheck.facade;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Transaction;
import com.arnaldsouza.fraudcheck.rule.FraudRule;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.arnaldsouza.fraudcheck.fixture.TestTransactions.withAmount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudCheckFacadeTest {

    private static final Transaction TRANSACTION = withAmount("100.00");
    private static final FraudRule PASSING = transaction -> Optional.empty();

    @Test
    void approvesWhenNoRuleIsViolated() {
        FraudCheckFacade facade = new FraudCheckFacade(List.of(PASSING, PASSING));

        assertTrue(facade.analyze(TRANSACTION).isApproved());
    }

    @Test
    void rejectsWithFirstViolationAndSkipsRemainingRules() {
        AtomicInteger lastRuleCalls = new AtomicInteger();
        FraudRule violated = transaction -> Optional.of("First violation");
        FraudRule last = transaction -> {
            lastRuleCalls.incrementAndGet();
            return Optional.of("Second violation");
        };

        AnalysisResult result = new FraudCheckFacade(List.of(PASSING, violated, last)).analyze(TRANSACTION);

        assertEquals("First violation", result.reason());
        assertEquals(0, lastRuleCalls.get());
    }
}