package com.vijay.security_jwt_aws_s3.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vijay.security_jwt_aws_s3.entity.InnerTokenResponse;
import com.vijay.security_jwt_aws_s3.entity.TokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

     @Value("${security.token.key}")
     private String securityKey;

     @Value("${security.token.expiry-time}")
     private Integer tokenExpiryTime;

     @Value("${security.token.referesh-time}")
     private Integer refreshTtokenExpiryTime;

     public TokenResponse generateToken(UserDetails user) {
          InnerTokenResponse accessToken = buildToken(user, tokenExpiryTime);
          InnerTokenResponse refereshToken = buildToken(user, refreshTtokenExpiryTime);

          return new TokenResponse(accessToken.getToken(), refereshToken.getToken(), accessToken.getIssuedAt(),
                    accessToken.getExpiresAt(), "BEARER");
     }

     public String getUserNameFromToken(String token) {
          return extractClaim(token, Claims::getSubject);
     }

     public boolean validateToken(String token, UserDetails userDetail) {
          final String userName = getUserNameFromToken(token);

          return (userDetail.getUsername().equals(userName) && !isTokeExpired(token));
     }

     public InnerTokenResponse buildToken(UserDetails user, Integer expiryTime) {

          Map<String, Object> claims = new HashMap<>();
          Date issueTime = new Date(System.currentTimeMillis());
          Date tokenExpiryTime = new Date(System.currentTimeMillis() + expiryTime);
         

          String token = Jwts.builder()
                    .claims()
                    .add(claims)
                    .subject(user.getUsername())
                    .issuedAt(issueTime)
                    .expiration(tokenExpiryTime)
                    .and()
                    .signWith(getKey())
                    .compact();

          return new InnerTokenResponse(token, issueTime, issueTime);

     }

     private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
          final Claims claims = extractAllClaims(token);
          return claimResolver.apply(claims);
     }

     private Claims extractAllClaims(String token) {
          return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
     }

     private SecretKey getKey() {
          byte[] keyBytes = Decoders.BASE64.decode(securityKey);
          return Keys.hmacShaKeyFor(keyBytes);
     }

     private boolean isTokeExpired(String token) {
          return extractExpiration(token).before(new Date());
     }

     private Date extractExpiration(String token) {
          return extractClaim(token, Claims::getExpiration);
     }

}
