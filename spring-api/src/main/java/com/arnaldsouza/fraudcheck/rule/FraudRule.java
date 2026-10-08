package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.Transaction;

import java.util.Optional;

/**
 * A single fraud check in the chain. Spring injects every implementation,
 * ordered by {@link org.springframework.core.annotation.Order}, into the facade.
 */
public interface FraudRule {

    /**
     * Returns the violation reason when the transaction breaks this rule,
     * or an empty Optional when it complies.
     */
    Optional<String> findViolation(Transaction transaction);
}