package com.jeeva.urlshortener.service;

import java.util.Optional;

public interface UrlService {
    String shortenUrl(String longUrl);
    Optional<String> getLongUrl(String shortUrl);
}
