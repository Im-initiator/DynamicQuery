package com.leminhtien.demoSpringJpaDynamic.service;

import com.leminhtien.demoSpringJpaDynamic.auth.details.CustomUserDetails;

public interface JwtService {
    String generateAccessToken(CustomUserDetails userDetails);
    String generateRefreshToken(CustomUserDetails userDetails);
    boolean isAccessTokenValid(String token,CustomUserDetails userDetails);
    boolean isRefreshTokenValid(String token,CustomUserDetails userDetails);
    public String extractAccountId (String token);
    public String extractUsername(String token);
}
