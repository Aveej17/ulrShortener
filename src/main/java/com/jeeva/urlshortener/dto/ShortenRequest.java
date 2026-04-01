package com.jeeva.urlshortener.dto;

public class ShortenRequest {
    private String longUrl;

    public ShortenRequest() {
    }

    public ShortenRequest(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
