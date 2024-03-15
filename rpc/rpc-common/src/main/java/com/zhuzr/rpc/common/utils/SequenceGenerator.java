package com.zhuzr.rpc.common.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    private static final AtomicInteger current = new AtomicInteger();

    public static int getNextSequence() {
        return current.getAndIncrement();
    }
}
