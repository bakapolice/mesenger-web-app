package com.example.messenger.security.service;

import com.example.messenger.payload.request.JwtRequest;
import com.example.messenger.payload.response.JwtResponse;
import com.example.messenger.payload.response.RefreshJwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
//    RefreshJwtResponse getAccessToken(String refreshToken);
    ResponseEntity<RefreshJwtResponse> refresh(String refreshToken);
    // JwtAuthentication getAuthInfo();
    ResponseEntity<JwtResponse> login(JwtRequest jwtRequest);
}
