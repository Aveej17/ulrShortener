package com.jeeva.urlshortener.repository;

import com.jeeva.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);

    // Optional (for idempotency improvement later)
    Optional<Url> findByLongUrl(String longUrl);
    Optional<Url> findByShortUrlAndShardId(String shortUrl, int shardId);
}

