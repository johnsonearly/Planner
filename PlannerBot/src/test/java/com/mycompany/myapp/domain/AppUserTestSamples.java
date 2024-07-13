package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AppUser getAppUserSample1() {
        return new AppUser().id(1L).name("name1").age(1).appUserId(1L);
    }

    public static AppUser getAppUserSample2() {
        return new AppUser().id(2L).name("name2").age(2).appUserId(2L);
    }

    public static AppUser getAppUserRandomSampleGenerator() {
        return new AppUser()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet())
            .appUserId(longCount.incrementAndGet());
    }
}
