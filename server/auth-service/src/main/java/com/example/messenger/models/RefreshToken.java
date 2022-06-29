package com.example.messenger.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class RefreshToken {
    private Long id;
    private User user;
    private String token;
    private Instant expiryDate;
}
