package com.jeeva.urlshortener.sharding;

import org.springframework.stereotype.Component;

@Component
public class ShardManager {

    private static final int TOTAL_SHARDS = 2; // start with 2

    public int getShardId(String shortUrl) {
        int hash = Math.abs(shortUrl.hashCode());
        return hash % TOTAL_SHARDS;
    }
}