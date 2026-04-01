package com.jeeva.urlshortener.encoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Slf4j
public class Base62Encoder implements ShortUrlEncoder{

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String encode(long input) {
        log.info("Encoding ID {} using Base62", input);
        StringBuilder encoded = new StringBuilder();
        int base = BASE62.length();

        while (input > 0) {
            int remainder = (int) (input % base);
            encoded.append(BASE62.charAt(remainder));
            input /= base;
        }

        return encoded.reverse().toString();
    }
}
