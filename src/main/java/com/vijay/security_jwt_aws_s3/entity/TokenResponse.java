package com.vijay.security_jwt_aws_s3.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenResponse {
     private String accessToken;
     private String refreshToken;
     private Date issuedAt;
     private Date expiresAt;
     private String tokenType;
}