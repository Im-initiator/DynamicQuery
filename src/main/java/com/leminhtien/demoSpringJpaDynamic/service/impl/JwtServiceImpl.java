package com.leminhtien.demoSpringJpaDynamic.service.impl;

import com.leminhtien.demoSpringJpaDynamic.auth.details.CustomUserDetails;
import com.leminhtien.demoSpringJpaDynamic.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.jwt.token.expired}")
    private long TOKEN_EXPIRED;

    @Value("${application.jwt.refresh-token.expired}")
    private long REFRESH_TOKEN_EXPIRED;

    private Key getSignInkey(){
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    @Override
    public String generateAccessToken(CustomUserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return generateToken(claims,TOKEN_EXPIRED,userDetails);
    }

    @Override
    public String generateRefreshToken(CustomUserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",userDetails.getAccount().getId());
        return generateToken(claims,REFRESH_TOKEN_EXPIRED,userDetails);
    }

    private String generateToken(Map<String, Object> claims, long tokenExpired, CustomUserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+tokenExpired))
                .compact();
    }

    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private  <T> T extractClaims(String token, Function<Claims,T> claimsResolve){
        Claims claims = extractAllClaim(token);
        return claimsResolve.apply(claims);
    }

    public String extractAccountId (String token){
        return extractClaims(token,(claim)->claim.get("id").toString());
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private Date extractExpired(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    @Override
    public boolean isAccessTokenValid(String token,CustomUserDetails userDetails){
        return (!extractExpired(token).before(new Date()))
                &&(userDetails.getUsername().equals(extractUsername(token)));
    }
    @Override
    public boolean isRefreshTokenValid(String token,CustomUserDetails userDetails){
        return (userDetails.getAccount().getId().toString().equals(extractAccountId(token)))
                &&(!extractExpired(token).before(new Date()))
                &&(userDetails.getUsername().equals(extractUsername(token)));
    }


}
