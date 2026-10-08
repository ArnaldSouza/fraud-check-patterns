package com.arnaldsouza.fraudcheck.facade;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Transaction;
import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;
import com.arnaldsouza.fraudcheck.rule.AmountLimitRule;
import com.arnaldsouza.fraudcheck.rule.BlockedAccountRule;
import com.arnaldsouza.fraudcheck.rule.FraudRule;
import com.arnaldsouza.fraudcheck.rule.UnusualHourRule;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * Single entry point for fraud analysis (Facade).
 * Hides how the rule chain is built and ordered from its clients.
 */
public final class FraudCheckFacade {

    private static final BigDecimal AMOUNT_LIMIT = new BigDecimal("5000.00");
    private static final LocalTime UNUSUAL_HOURS_START = LocalTime.of(22, 0);
    private static final LocalTime UNUSUAL_HOURS_END = LocalTime.of(6, 0);

    private final FraudRule chain;

    public FraudCheckFacade(BlockedAccountRegistry registry) {
        this.chain = buildChain(registry);
    }

    public AnalysisResult analyze(Transaction transaction) {
        return chain.check(transaction);
    }

    private static FraudRule buildChain(BlockedAccountRegistry registry) {
        FraudRule head = new BlockedAccountRule(registry);
        head.linkWith(new AmountLimitRule(AMOUNT_LIMIT))
                .linkWith(new UnusualHourRule(UNUSUAL_HOURS_START, UNUSUAL_HOURS_END));
        return head;
    }
}