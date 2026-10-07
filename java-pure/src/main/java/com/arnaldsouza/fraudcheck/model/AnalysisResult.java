package com.arnaldsouza.fraudcheck.model;

import java.util.Objects;

public record AnalysisResult(Decision decision, String reason) {

    private static final String NO_VIOLATION_REASON = "No fraud rule violated";

    public AnalysisResult {
        Objects.requireNonNull(decision, "decision must not be null");
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be blank");
        }
    }

    public static AnalysisResult approved() {
        return new AnalysisResult(Decision.APPROVED, NO_VIOLATION_REASON);
    }

    public static AnalysisResult rejected(String reason) {
        return new AnalysisResult(Decision.REJECTED, reason);
    }

    public boolean isApproved() {
        return decision == Decision.APPROVED;
    }
}