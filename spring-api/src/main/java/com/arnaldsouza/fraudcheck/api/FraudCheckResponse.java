package com.arnaldsouza.fraudcheck.api;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Decision;

/** Response body for a fraud check. */
public record FraudCheckResponse(String transactionId, Decision decision, String reason) {

    public static FraudCheckResponse of(String transactionId, AnalysisResult result) {
        return new FraudCheckResponse(transactionId, result.decision(), result.reason());
    }
}