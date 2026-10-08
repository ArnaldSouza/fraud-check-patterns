package com.arnaldsouza.fraudcheck.fixture;

import com.arnaldsouza.fraudcheck.config.FraudCheckProperties;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;

/** Builds the same thresholds as application.properties, without starting Spring. */
public final class TestProperties {

    private TestProperties() {
    }

    public static FraudCheckProperties withBlockedAccounts(String... accountIds) {
        return new FraudCheckProperties(
                new BigDecimal("5000.00"), LocalTime.of(22, 0), LocalTime.of(6, 0), Set.of(accountIds));
    }
}