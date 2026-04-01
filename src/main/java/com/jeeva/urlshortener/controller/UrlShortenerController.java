package com.jeeva.urlshortener.controller;

import com.jeeva.urlshortener.dto.ShortenRequest;
import com.jeeva.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    private final UrlService urlService;

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody ShortenRequest request) {
        return ResponseEntity.ok(urlService.shortenUrl(request.getLongUrl()));
    }

    @GetMapping("/{input}")
    public ResponseEntity<?> redirect(@PathVariable String input) {

        // If it's a real URL → redirect directly
        if (input.contains(".")) {
            String url = input.startsWith("http") ? input : "https://" + input;
            return ResponseEntity.status(302)
                    .header("Location", url)
                    .build();
        }

        // Otherwise treat as short URL
        return urlService.getLongUrl(input)
                .map(url -> ResponseEntity.status(302)
                        .header("Location", url)
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }
}
