package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.config.FraudCheckProperties;
import com.arnaldsouza.fraudcheck.model.Transaction;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Rejects transactions inside a suspicious time window [start, end).
 * Supports windows that cross midnight, such as 22:00 to 06:00.
 */
@Component
@Order(RuleOrder.UNUSUAL_HOUR)
public class UnusualHourRule implements FraudRule {

    private static final String VIOLATION = "Transaction at unusual hour";

    private final LocalTime windowStart;
    private final LocalTime windowEnd;

    public UnusualHourRule(FraudCheckProperties properties) {
        this.windowStart = properties.unusualHoursStart();
        this.windowEnd = properties.unusualHoursEnd();
    }

    @Override
    public Optional<String> findViolation(Transaction transaction) {
        return isInsideWindow(transaction.timestamp().toLocalTime())
                ? Optional.of(VIOLATION)
                : Optional.empty();
    }

    private boolean isInsideWindow(LocalTime time) {
        boolean afterStart = !time.isBefore(windowStart);
        boolean beforeEnd = time.isBefore(windowEnd);
        return crossesMidnight() ? afterStart || beforeEnd : afterStart && beforeEnd;
    }

    private boolean crossesMidnight() {
        return windowStart.isAfter(windowEnd);
    }
}