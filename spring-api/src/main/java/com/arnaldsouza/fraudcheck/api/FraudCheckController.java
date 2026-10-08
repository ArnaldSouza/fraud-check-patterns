package com.arnaldsouza.fraudcheck.api;

import com.arnaldsouza.fraudcheck.facade.FraudCheckFacade;
import com.arnaldsouza.fraudcheck.model.Transaction;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fraud-checks")
public class FraudCheckController {

    private final FraudCheckFacade facade;

    public FraudCheckController(FraudCheckFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public FraudCheckResponse analyze(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = request.toDomain();
        return FraudCheckResponse.of(transaction.id(), facade.analyze(transaction));
    }
}