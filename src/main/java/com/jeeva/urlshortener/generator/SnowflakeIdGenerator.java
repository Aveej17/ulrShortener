package com.jeeva.urlshortener.generator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SnowflakeIdGenerator implements IdGenerator {

    private final long EPOCH = 1704067200000L; // Jan 1, 2024 (custom epoch)

    private final long DATACENTER_ID = 1L;
    private final long MACHINE_ID = 1L;

    private final long SEQUENCE_BITS = 12;
    private final long MACHINE_ID_BITS = 5;
    private final long DATACENTER_ID_BITS = 5;

    private final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private final long MACHINE_SHIFT = SEQUENCE_BITS;
    private final long DATACENTER_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;
    private final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS + DATACENTER_ID_BITS;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    @Override
    public synchronized long generateId() {

        long currentTimestamp = currentTime();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;

            if (sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (DATACENTER_ID << DATACENTER_SHIFT)
                | (MACHINE_ID << MACHINE_SHIFT)
                | sequence;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = currentTime();
        }
        return currentTimestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }
}