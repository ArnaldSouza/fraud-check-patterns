    package com.arnaldsouza.fraudcheck.rule;

/**
 * Position of each rule in the chain. Kept in one place so the full order
 * can be read at a glance, as the buildChain method does in the plain Java version.
 */
public final class RuleOrder {

    public static final int BLOCKED_ACCOUNT = 1;
    public static final int AMOUNT_LIMIT = 2;
    public static final int UNUSUAL_HOUR = 3;

    private RuleOrder() {
    }
}