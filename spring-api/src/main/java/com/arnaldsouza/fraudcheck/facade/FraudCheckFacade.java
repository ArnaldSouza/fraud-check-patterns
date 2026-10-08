package com.arnaldsouza.fraudcheck.facade;

import com.arnaldsouza.fraudcheck.model.AnalysisResult;
import com.arnaldsouza.fraudcheck.model.Transaction;
import com.arnaldsouza.fraudcheck.rule.FraudRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Single entry point for fraud analysis (Facade).
 * Spring injects every FraudRule bean already sorted by @Order;
 * the first violation found rejects the transaction (Chain of Responsibility).
 */
@Service
public class FraudCheckFacade {

    private final List<FraudRule> rules;

    public FraudCheckFacade(List<FraudRule> rules) {
        this.rules = List.copyOf(rules);
    }

    public AnalysisResult analyze(Transaction transaction) {
        return rules.stream()
                .map(rule -> rule.findViolation(transaction))
                .flatMap(Optional::stream)
                .findFirst()
                .map(AnalysisResult::rejected)
                .orElseGet(AnalysisResult::approved);
    }
}