package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Transaction;

import java.util.Optional;

/**
 * Base handler of the fraud check chain (Chain of Responsibility).
 * Each rule either rejects the transaction or delegates to the next rule;
 * a transaction that passes every rule is approved.
 */
public abstract class FraudRule {

    private FraudRule next;

    /**
     * Links the given rule after this one and returns it, allowing fluent
     * chaining: {@code first.linkWith(second).linkWith(third)}.
     */
    public FraudRule linkWith(FraudRule next) {
        this.next = next;
        return next;
    }

    public final AnalysisResult check(Transaction transaction) {
        Optional<String> violation = findViolation(transaction);
        if (violation.isPresent()) {
            return AnalysisResult.rejected(violation.get());
        }
        return next == null ? AnalysisResult.approved() : next.check(transaction);
    }

    /**
     * Returns the violation reason when the transaction breaks this rule,
     * or an empty Optional when it complies.
     */
    protected abstract Optional<String> findViolation(Transaction transaction);
}