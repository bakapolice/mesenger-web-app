package com.example.messenger.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshJwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
