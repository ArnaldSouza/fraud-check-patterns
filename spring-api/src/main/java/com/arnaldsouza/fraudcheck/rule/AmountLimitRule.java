package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.config.FraudCheckProperties;
import com.arnaldsouza.fraudcheck.model.Transaction;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Order(RuleOrder.AMOUNT_LIMIT)
public class AmountLimitRule implements FraudRule {

    private final BigDecimal limit;

    public AmountLimitRule(FraudCheckProperties properties) {
        this.limit = properties.amountLimit();
    }

    @Override
    public Optional<String> findViolation(Transaction transaction) {
        return transaction.amount().compareTo(limit) > 0
                ? Optional.of("Amount exceeds limit of " + limit)
                : Optional.empty();
    }
}