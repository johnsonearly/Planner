package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FreeTimeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FreeTime getFreeTimeSample1() {
        return new FreeTime().id(1L);
    }

    public static FreeTime getFreeTimeSample2() {
        return new FreeTime().id(2L);
    }

    public static FreeTime getFreeTimeRandomSampleGenerator() {
        return new FreeTime().id(longCount.incrementAndGet());
    }
}
