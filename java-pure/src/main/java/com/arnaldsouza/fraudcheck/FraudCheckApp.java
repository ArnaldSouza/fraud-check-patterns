package com.arnaldsouza.fraudcheck;

import com.arnaldsouza.fraudcheck.facade.FraudCheckFacade;
import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Transaction;
import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public final class FraudCheckApp {

    private static final String BLOCKED_ACCOUNT_ID = "acc-blocked";

    private FraudCheckApp() {
    }

    public static void main(String[] args) {
        BlockedAccountRegistry registry = BlockedAccountRegistry.getInstance();
        registry.block(BLOCKED_ACCOUNT_ID);

        FraudCheckFacade facade = new FraudCheckFacade(registry);
        sampleTransactions().forEach(transaction -> print(transaction, facade.analyze(transaction)));
    }

    private static List<Transaction> sampleTransactions() {
        LocalDateTime businessHours = LocalDateTime.of(2026, 10, 8, 14, 30);
        LocalDateTime lateNight = LocalDateTime.of(2026, 10, 8, 2, 15);
        return List.of(
                new Transaction("tx-1", "acc-100", new BigDecimal("250.00"), businessHours),
                new Transaction("tx-2", BLOCKED_ACCOUNT_ID, new BigDecimal("250.00"), businessHours),
                new Transaction("tx-3", "acc-200", new BigDecimal("12000.00"), businessHours),
                new Transaction("tx-4", "acc-300", new BigDecimal("80.00"), lateNight));
    }

    private static void print(Transaction transaction, AnalysisResult result) {
        System.out.printf("%s | %-8s | %s%n", transaction.id(), result.decision(), result.reason());
    }
}