package com.vijay.security_jwt_aws_s3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.security_jwt_aws_s3.entity.TokenResponse;
import com.vijay.security_jwt_aws_s3.entity.User;
import com.vijay.security_jwt_aws_s3.service.SessionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/session")
@AllArgsConstructor
public class SessionController {

    private SessionService sessionService;

    @PostMapping("signIn")
    public ResponseEntity<TokenResponse> signIn(@RequestBody User user) {
        return new ResponseEntity<>(sessionService.signIn(user), HttpStatus.OK);
    }

    @PostMapping("signUp")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return new ResponseEntity<>(sessionService.signUp(user), HttpStatus.OK);
    }

}
