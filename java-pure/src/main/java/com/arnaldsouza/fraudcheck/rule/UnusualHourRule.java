package com.arnaldsouza.fraudcheck.rule;

import com.arnaldsouza.fraudcheck.model.Transaction;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Rejects transactions inside a suspicious time window [start, end).
 * Supports windows that cross midnight, such as 22:00 to 06:00.
 */
public final class UnusualHourRule extends FraudRule {

    private static final String VIOLATION = "Transaction at unusual hour";

    private final LocalTime windowStart;
    private final LocalTime windowEnd;

    public UnusualHourRule(LocalTime windowStart, LocalTime windowEnd) {
        this.windowStart = Objects.requireNonNull(windowStart, "windowStart must not be null");
        this.windowEnd = Objects.requireNonNull(windowEnd, "windowEnd must not be null");
    }

    @Override
    protected Optional<String> findViolation(Transaction transaction) {
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