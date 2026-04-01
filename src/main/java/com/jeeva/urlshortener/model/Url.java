package com.jeeva.urlshortener.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls",
        indexes = {
                @Index(name = "idx_short_url", columnList = "shortUrl", unique = true)
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String shortUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Optional but useful (future scalability features)
    private LocalDateTime expiryTime;

    private Long userId; // for multi-user support later
}