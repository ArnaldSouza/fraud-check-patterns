package com.arnaldsouza.fraudcheck.registry;

import com.arnaldsouza.fraudcheck.config.FraudCheckProperties;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry of blocked accounts. Spring beans are singletons by default,
 * so no private constructor or getInstance() is needed.
 */
@Component
public class BlockedAccountRegistry {

    private final Set<String> blockedAccountIds = ConcurrentHashMap.newKeySet();

    public BlockedAccountRegistry(FraudCheckProperties properties) {
        blockedAccountIds.addAll(properties.blockedAccountIds());
    }

    public boolean isBlocked(String accountId) {
        return blockedAccountIds.contains(accountId);
    }
}