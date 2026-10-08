package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.Transaction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public final class AmountLimitRule extends FraudRule {

    private final BigDecimal limit;

    public AmountLimitRule(BigDecimal limit) {
        this.limit = Objects.requireNonNull(limit, "limit must not be null");
    }

    @Override
    protected Optional<String> findViolation(Transaction transaction) {
        return transaction.amount().compareTo(limit) > 0
                ? Optional.of("Amount exceeds limit of " + limit)
                : Optional.empty();
    }
}