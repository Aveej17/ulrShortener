package com.jeeva.urlshortener.datastore.impl;

import com.jeeva.urlshortener.datastore.UrlDataStore;
import com.jeeva.urlshortener.model.Url;
import com.jeeva.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrimaryDataStore implements UrlDataStore {

    private final UrlRepository urlRepository;

    public PrimaryDataStore(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public void save(Url url) {
        urlRepository.save(url);
    }

    @Override
    public Optional<Url> findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    @Override
    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }
}