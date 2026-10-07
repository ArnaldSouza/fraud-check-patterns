package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Decision;
import com.arnaldsouza.fraudcheck.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudRuleTest {

    private static final Transaction TRANSACTION = new Transaction(
            "tx-1", "acc-1", new BigDecimal("100.00"), LocalDateTime.of(2026, 10, 7, 14, 0));

    @Test
    void approvesWhenNoRuleIsViolated() {
        StubRule first = StubRule.passing();
        StubRule second = StubRule.passing();
        first.linkWith(second);

        AnalysisResult result = first.check(TRANSACTION);

        assertTrue(result.isApproved());
        assertEquals(1, first.invocations);
        assertEquals(1, second.invocations);
    }

    @Test
    void rejectsWithReasonOfFirstViolatedRuleAndStopsTheChain() {
        StubRule first = StubRule.passing();
        StubRule violated = StubRule.violating("Amount above limit");
        StubRule last = StubRule.passing();
        first.linkWith(violated).linkWith(last);

        AnalysisResult result = first.check(TRANSACTION);

        assertEquals(Decision.REJECTED, result.decision());
        assertEquals("Amount above limit", result.reason());
        assertEquals(0, last.invocations);
    }

    @Test
    void singleRuleWithoutNextApprovesCompliantTransaction() {
        assertTrue(StubRule.passing().check(TRANSACTION).isApproved());
    }

    /** Test double that records invocations and returns a fixed outcome. */
    private static final class StubRule extends FraudRule {

        private final String violationReason;
        private int invocations;

        private StubRule(String violationReason) {
            this.violationReason = violationReason;
        }

        static StubRule passing() {
            return new StubRule(null);
        }

        static StubRule violating(String reason) {
            return new StubRule(reason);
        }

        @Override
        protected Optional<String> findViolation(Transaction transaction) {
            invocations++;
            return Optional.ofNullable(violationReason);
        }
    }
}