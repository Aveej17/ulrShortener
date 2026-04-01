package com.jeeva.urlshortener.service.impl;

import com.jeeva.urlshortener.encoder.ShortUrlEncoder;
import com.jeeva.urlshortener.generator.IdGenerator;
import com.jeeva.urlshortener.model.Url;
import com.jeeva.urlshortener.repository.UrlRepository;
import com.jeeva.urlshortener.service.UrlService;
import com.jeeva.urlshortener.exception.InvalidUrlException;
import com.jeeva.urlshortener.exception.UrlNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final IdGenerator idGenerator;
    private final ShortUrlEncoder encoder;

    public UrlServiceImpl(UrlRepository urlRepository,
                          IdGenerator idGenerator,
                          ShortUrlEncoder encoder) {
        this.urlRepository = urlRepository;
        this.idGenerator = idGenerator;
        this.encoder = encoder;
    }

    /**
     * Shortens a given long URL
     */
    @Override
    @Transactional
    public String shortenUrl(String longUrl) {

//        validateUrl(longUrl);

        long id = idGenerator.generateId();
        String shortUrl = encoder.encode(id);
        log.info("shortUrl => "+ shortUrl + " generated for " + longUrl);

        Url url = Url.builder()
                .id(id)
                .shortUrl(shortUrl)
                .longUrl(longUrl)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Saving URL mapping: {} -> {}", shortUrl, longUrl);

        urlRepository.save(url);

        return shortUrl;
    }

    /**
     * Fetch original URL from short URL
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<String> getLongUrl(String shortUrl) {

        log.info("Fetching long URL for shortUrl={}", shortUrl);

        if (shortUrl == null || shortUrl.isBlank()) {
            throw new InvalidUrlException("Short URL cannot be null or empty");
        }

        return urlRepository.findByShortUrl(shortUrl)
                .map(url -> {
                    log.info("Redirecting shortUrl={} to longUrl={}", shortUrl, url.getLongUrl());
                    return url.getLongUrl();
                });
    }

    /**
     * Validate URL format
     */
    private void validateUrl(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) {
            throw new InvalidUrlException("URL cannot be null or empty");
        }

        try {
            new URL(longUrl); // validates format
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("Invalid URL format: " + longUrl);
        }
    }
}