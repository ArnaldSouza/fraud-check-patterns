package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.Transaction;
import com.arnaldsouza.fraudcheck.registry.BlockedAccountRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(RuleOrder.BLOCKED_ACCOUNT)
public class BlockedAccountRule implements FraudRule {

    private static final String VIOLATION = "Account is blocked";

    private final BlockedAccountRegistry registry;

    public BlockedAccountRule(BlockedAccountRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Optional<String> findViolation(Transaction transaction) {
        return registry.isBlocked(transaction.accountId())
                ? Optional.of(VIOLATION)
                : Optional.empty();
    }
}