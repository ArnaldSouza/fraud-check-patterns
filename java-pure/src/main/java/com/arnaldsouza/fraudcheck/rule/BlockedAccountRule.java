package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.Transaction;
import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;

import java.util.Objects;
import java.util.Optional;

public final class BlockedAccountRule extends FraudRule {

    private static final String VIOLATION = "Account is blocked";

    private final BlockedAccountRegistry registry;

    public BlockedAccountRule(BlockedAccountRegistry registry) {
        this.registry = Objects.requireNonNull(registry, "registry must not be null");
    }

    @Override
    protected Optional<String> findViolation(Transaction transaction) {
        return registry.isBlocked(transaction.accountId())
                ? Optional.of(VIOLATION)
                : Optional.empty();
    }
}