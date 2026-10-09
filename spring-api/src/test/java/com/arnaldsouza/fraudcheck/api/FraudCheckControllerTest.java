package com.arnaldsouza.fraudcheck.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FraudCheckControllerTest {

    private static final String ENDPOINT = "/api/fraud-checks";

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void approvesRegularTransaction() throws Exception {
        postTransaction("""
                {"id": "tx-1", "accountId": "acc-100", "amount": 250.00, "timestamp": "2026-10-08T14:30:00"}
                """)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("tx-1"))
                .andExpect(jsonPath("$.decision").value("APPROVED"));
    }

    @Test
    void reportsBlockedAccountFirstWhenSeveralRulesAreViolated() throws Exception {
        postTransaction("""
                {"id": "tx-2", "accountId": "acc-blocked", "amount": 12000.00, "timestamp": "2026-10-08T02:15:00"}
                """)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("REJECTED"))
                .andExpect(jsonPath("$.reason").value("Account is blocked"));
    }

    @Test
    void returnsValidationErrorForNonPositiveAmount() throws Exception {
        postTransaction("""
                {"id": "tx-3", "accountId": "acc-100", "amount": -5, "timestamp": "2026-10-08T14:30:00"}
                """)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.amount").value("must be greater than 0"));
    }

    @Test
    void rejectsIdContainingLineBreak() throws Exception {
        postTransaction("""
                {"id": "tx\\nforged", "accountId": "acc-100", "amount": 250.00, "timestamp": "2026-10-08T14:30:00"}
                """)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.id").exists());
    }

    @Test
    void returnsGenericErrorForMalformedBody() throws Exception {
        postTransaction("{ not valid json")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Malformed request body"));
    }

    private ResultActions postTransaction(String json) throws Exception {
        return mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }
}