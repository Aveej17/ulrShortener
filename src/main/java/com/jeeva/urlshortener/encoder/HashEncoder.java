package com.jeeva.urlshortener.encoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HashEncoder implements ShortUrlEncoder {
    @Override
    public String encode(long input) {
        log.info("Encoding ID {} using HshEncoder", input);
//        return someHashLogic(input);
        return null;
    }
}