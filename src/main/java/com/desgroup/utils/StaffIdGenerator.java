package com.desgroup.utils;

import java.util.concurrent.atomic.AtomicInteger;

public final class StaffIdGenerator {
    private static final AtomicInteger next = new AtomicInteger(1);

    private StaffIdGenerator() {
    }

    public static int next() {
        return next.getAndIncrement();
    }
}
