package com.jeeva.urlshortener.datastore.impl;

import com.jeeva.urlshortener.datastore.UrlDataStore;
import com.jeeva.urlshortener.model.Url;
import com.jeeva.urlshortener.repository.UrlRepository;
import com.jeeva.urlshortener.sharding.ShardManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
@Slf4j
public class ShardedDataStore implements UrlDataStore {

    private final ShardManager shardManager;
    private final UrlRepository urlRepository;

    public ShardedDataStore(ShardManager shardManager,
                            UrlRepository urlRepository) {
        this.shardManager = shardManager;
        this.urlRepository = urlRepository;
    }

    @Override
    public void save(Url url) {
        int shardId = shardManager.getShardId(url.getShortUrl());
        url.setShardId(shardId);

        log.info("Saving to shard {}", shardId);

        urlRepository.save(url);
    }

    @Override
    public Optional<Url> findByShortUrl(String shortUrl) {
        int shardId = shardManager.getShardId(shortUrl);

        log.info("Fetching from shard {}", shardId);

        return urlRepository.findByShortUrlAndShardId(shortUrl, shardId);
    }

    @Override
    public Optional<Url> findByLongUrl(String longUrl) {
        // For now: fallback (not optimal but acceptable)
        return urlRepository.findByLongUrl(longUrl);
    }
}