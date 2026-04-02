package com.jeeva.urlshortener.datastore;

import com.jeeva.urlshortener.model.Url;

import java.util.Optional;

public interface UrlDataStore {

    void save(Url url);

    Optional<Url> findByShortUrl(String shortUrl);

    Optional<Url> findByLongUrl(String longUrl);
}