package com.arnaldsouza.fraudcheck.api;

import com.arnaldsouza.fraudcheck.model.Transaction;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Request body for a fraud check. Validated before reaching the domain. */
public record TransactionRequest(
        @NotBlank @Size(max = 64) @Pattern(regexp = ID_PATTERN) String id,
        @NotBlank @Size(max = 64) @Pattern(regexp = ID_PATTERN) String accountId,
        @NotNull @Positive @Digits(integer = 12, fraction = 2) BigDecimal amount,
        @NotNull LocalDateTime timestamp) {

    private static final String ID_PATTERN = "^[A-Za-z0-9-]+$";

    public Transaction toDomain() {
        return new Transaction(id, accountId, amount, timestamp);
    }
}