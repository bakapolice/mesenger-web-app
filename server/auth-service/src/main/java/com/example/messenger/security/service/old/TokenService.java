package com.example.messenger.security.service.old;

public interface TokenService {
    String generateAccessToken(String userId, String userLogin);
    String generateRefreshToken(String userId);
    boolean checkToken(String token, String secretKey);
    boolean checkAccessToken(String accessToken);
    boolean checkRefreshToken(String refreshToken);
    String getUserIdFromToken(String token, String secretKey);
    String getUserIdFromAccessToken(String token);
    String getUserIdFromRefreshToken(String token);
}
