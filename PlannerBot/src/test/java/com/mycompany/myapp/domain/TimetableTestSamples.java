package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TimetableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Timetable getTimetableSample1() {
        return new Timetable().id(1L).appUserId(1L).dayOfWeek("dayOfWeek1").activity("activity1");
    }

    public static Timetable getTimetableSample2() {
        return new Timetable().id(2L).appUserId(2L).dayOfWeek("dayOfWeek2").activity("activity2");
    }

    public static Timetable getTimetableRandomSampleGenerator() {
        return new Timetable()
            .id(longCount.incrementAndGet())
            .appUserId(longCount.incrementAndGet())
            .dayOfWeek(UUID.randomUUID().toString())
            .activity(UUID.randomUUID().toString());
    }
}
