package com.arnaldsouza.fraudcheck.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;

/** Fraud rule thresholds, bound from the "fraud-check" prefix in application.properties. */
@Validated
@ConfigurationProperties(prefix = "fraud-check")
public record FraudCheckProperties(
        @NotNull @Positive BigDecimal amountLimit,
        @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime unusualHoursStart,
        @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime unusualHoursEnd,
        @DefaultValue Set<String> blockedAccountIds) {
}