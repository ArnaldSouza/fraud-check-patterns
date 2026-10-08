package com.arnaldsouza.fraudcheck.registry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class BlockedAccountRegistryTest {

    @Test
    void alwaysReturnsTheSameInstance() {
        assertSame(BlockedAccountRegistry.getInstance(), BlockedAccountRegistry.getInstance());
    }
}