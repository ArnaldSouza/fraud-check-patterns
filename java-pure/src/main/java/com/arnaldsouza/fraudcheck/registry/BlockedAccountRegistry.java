package com.arnaldsouza.fraudcheck.registry;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Application-wide registry of blocked accounts (Singleton).
 * Uses the initialization-on-demand holder idiom: lazy and thread-safe
 * without explicit synchronization.
 */
public final class BlockedAccountRegistry {

    private final Set<String> blockedAccountIds = ConcurrentHashMap.newKeySet();

    private BlockedAccountRegistry() {
    }

    public static BlockedAccountRegistry getInstance() {
        return Holder.INSTANCE;
    }

    public void block(String accountId) {
        blockedAccountIds.add(Objects.requireNonNull(accountId, "accountId must not be null"));
    }

    public void unblock(String accountId) {
        blockedAccountIds.remove(accountId);
    }

    public boolean isBlocked(String accountId) {
        return blockedAccountIds.contains(accountId);
    }

    private static final class Holder {
        private static final BlockedAccountRegistry INSTANCE = new BlockedAccountRegistry();
    }
}