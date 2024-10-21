package com.vijay.security_jwt_aws_s3.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InnerTokenResponse {
     private String token;
     private Date issuedAt;
     private Date expiresAt;
}
